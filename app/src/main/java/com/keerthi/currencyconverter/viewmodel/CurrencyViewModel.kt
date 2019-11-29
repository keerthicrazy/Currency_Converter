package com.keerthi.currencyconverter.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.AndroidViewModel
import com.keerthi.currencyconverter.R
import com.keerthi.currencyconverter.data.AvailableCurrencies
import com.keerthi.currencyconverter.data.Currency
import com.keerthi.currencyconverter.data.CurrencyReporsitory
import com.keerthi.currencyconverter.model.*
import com.keerthi.currencyconverter.network.ResponseRetrofit
import com.keerthi.currencyconverter.network.RetrofitService
import com.keerthi.currencyconverter.utils.Constants
import com.keerthi.currencyconverter.utils.CurrencyUtils
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This class is act as presenter or view model, fetch and manipulate the data based on user inputs
 *
 */
class CurrencyViewModel(application: Application) : AndroidViewModel(application),
    ResponseRetrofit {

    private var context: Context = application.applicationContext

    private var retrofitService: RetrofitService? = null

    private var currencyListener: CurrencyListener? = null

    private var currencyReporsitory: CurrencyReporsitory = CurrencyReporsitory(application)

    private var lastUpdatedCurrency: Currency? = null

    private var availableCurrencies: AvailableCurrencies? = null

    private var srcCurrencyValue: Double = 1.0

    private lateinit var srcCurrency: String

    init {
        retrofitService = RetrofitService.getRetrofit()
        retrofitService?.getRetrofitService(this)
    }

    fun initListener(currencyListener: CurrencyListener) {
        this.currencyListener = currencyListener
    }

    /**
     * This method is used to get Available Currencies from Database / API
     *
     * First it will check the local database, whether data is available of not
     *
     * If available means, it will fetcb from local db, otherwise it will fetch the data from API
     */
    fun getAvailableCurrencies(srcCurrency: String, srcCurrencyValue: Double) {

        availableCurrencies = currencyReporsitory.fetchAvailableCurrencies();

        if (null != availableCurrencies) {
            AvailableCountriesSingleton.getInstance().availableCountries = availableCurrencies
            getCurrency(srcCurrency, srcCurrencyValue)
        } else {
            this.srcCurrency = srcCurrency
            this.srcCurrencyValue = srcCurrencyValue
            if (checkInternetConnection()) {
                currencyListener!!.showLoader(true)
                retrofitService?.getAvailableCurrencies()
            } else {
                currencyListener?.showInternetMessage()
            }
        }
    }

    /**
     * This method is used to get Available Currencies Exchange values from Database / API
     *
     * First it will check the local database, id data is available, will check the last updated time.
     *
     * If time difference is greater than 30 mins means, again it will fetch the data from API.
     *
     * Otherwise it will fetch the data from db
     */
    fun getCurrency(srcCurrency: String, srcCurrencyValue: Double) {

        this.srcCurrency = srcCurrency
        this.srcCurrencyValue = srcCurrencyValue

        lastUpdatedCurrency = currencyReporsitory.getLastCurrencyData()

        if (null != lastUpdatedCurrency) {

            val timeDifference =
                Date().time - CurrencyUtils.convertTimeStampToDate(lastUpdatedCurrency?.createdAt)!!.time

            val minutes = timeDifference / (60 * 1000) % 60;

            if (minutes > 30) {
                currencyListener!!.showLoader(true)
                retrofitService?.getCurrencyRates()
            } else {
                currencyListener?.postCurrencyData(extractCurrencyData(lastUpdatedCurrency!!))
            }
        } else {
            if (checkInternetConnection()) {
                currencyListener!!.showLoader(true)
                retrofitService?.getCurrencyRates()
            } else {
                currencyListener?.showInternetMessage()
            }

        }


    }

    /**
     * This listener method will transfer the Currency Exchange API response from API Service
     */
    override fun requestSuccess(data: CurrencyApiResponse) {

        val currencyApiResponse = data

        currencyListener!!.showLoader(false)

        if (currencyApiResponse.succuss) {

            val currency = Currency(
                currencyApiResponse.quotes,
                currencyApiResponse.source,
                Date().time
            );

            currencyReporsitory.insert(currency)

            lastUpdatedCurrency = currencyReporsitory.getLastCurrencyData()!!

            if (null != lastUpdatedCurrency) {
                currencyListener?.postCurrencyData(extractCurrencyData(lastUpdatedCurrency!!));
            } else {
                currencyListener?.showErrorMessage(context.getString(R.string.something_went_wrong))
            }


        } else {

            var errorMsg = currencyApiResponse.error.info

            if (errorMsg.isEmpty()) {
                errorMsg = context.getString(R.string.something_went_wrong)
            }

            currencyListener?.showErrorMessage(errorMsg)
        }

    }

    /**
     * This method is used to extract the currency data which fetched from API / local database
     *
     * Also, it will get the currency Name based on Currency Code
     */
    private fun extractCurrencyData(currency: Currency): ExchangeCurrency {

        val allCurrencies: ArrayList<ExtCurrencyDetail> = ArrayList()

        val srcCurrencyUsValue = currency.rates.get("USD$srcCurrency")
        val usMarketValue = 1.0 / srcCurrencyUsValue!!

        for (entry in currency.rates.entries) {

            val currencyCode = entry.key.substring(3)

            if (srcCurrency != currencyCode) {

                val currencyName =
                    AvailableCountriesSingleton.getInstance().getCurrencyName(currencyCode)

                var currencyValue = 0.0;

                if (srcCurrency != Constants.DEFAULT_SOURCE_CURRENCY) {
                    currencyValue = (srcCurrencyValue * usMarketValue) * entry.value
                } else {

                    currencyValue = srcCurrencyValue * entry.value
                }


                val extCurrency = ExtCurrencyDetail(
                    currencyCode, currencyName!!, currencyValue
                )
                allCurrencies.add(extCurrency)
            }

        }

        val exchangeCurrency = ExchangeCurrency(
            currency.src, CurrencyUtils.convertTimeStamp(currency.createdAt)!!, allCurrencies
        )
        return exchangeCurrency
    }


    /**
     * This listener method is used to capture the api response failure
     */
    override fun requestFailure() {
        currencyListener!!.showLoader(false)
        currencyListener?.showErrorMessage(context.getString(R.string.something_went_wrong))
    }

    /**
     * This listener method is used to capture the http errors.
     */
    override fun requestInternetIssue() {
        currencyListener!!.showLoader(false)
        currencyListener?.showInternetMessage()
    }

    /**
     * This listener method will transfer the list of available currencies API response from API Service
     */
    override fun requestCountryApiResponse(data: CountryApiResponse) {

        val countryApiResponse = data

        if (countryApiResponse.success) {
            getCurrency(srcCurrency, srcCurrencyValue)

            val availableCurrencies = AvailableCurrencies(countryApiResponse.currencies)
            currencyReporsitory.insertAvailableCountries(availableCurrencies)

            AvailableCountriesSingleton.getInstance().availableCountries =
                currencyReporsitory.fetchAvailableCurrencies()

        } else {
            var errorMsg = countryApiResponse.error.info

            if (errorMsg.isEmpty()) {
                errorMsg = context.getString(R.string.something_went_wrong)
            }
            currencyListener?.showErrorMessage(errorMsg)
        }
    }

    /**
     * This method is used to check the internet connection
     */
    fun checkInternetConnection(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

}