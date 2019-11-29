package com.keerthi.currencyconverter.network

import com.keerthi.currencyconverter.BuildConfig
import com.keerthi.currencyconverter.model.CountryApiResponse
import com.keerthi.currencyconverter.model.CurrencyApiResponse
import com.keerthi.currencyconverter.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This RetrofitService class is used to REST API Calls
 *
 * Give Request to server and fetch the issue.
 */
class RetrofitService : Callback<CurrencyApiResponse> {

    private var currencyApiService: CurrencyApiService? = null
    private lateinit var listener: ResponseRetrofit
    private var instance: Retrofit? = null

    fun getRetrofitInstance(): Retrofit? {

        if (instance == null) {

            val interceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            }
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            instance = Retrofit.Builder().baseUrl(Constants.BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance
    }

    companion object {
        private var retrofitService: RetrofitService? = null

        fun getRetrofit(): RetrofitService? {

            if (retrofitService == null) {
                retrofitService = RetrofitService()
            }

            return retrofitService
        }
    }

    fun getRetrofitService(listener: ResponseRetrofit) {
        currencyApiService = getRetrofitInstance()?.create(CurrencyApiService::class.java)
        this.listener = listener
    }

    fun getCurrencyRates() {
        currencyApiService?.getCurrencyLiveData(
            Constants.API_ACCESS_KEY,
            Constants.API_RETURN_FORMAT
        )?.enqueue(this)
    }

    fun getAvailableCurrencies() {
        currencyApiService?.getAvailableCurrencies(Constants.API_ACCESS_KEY)
            ?.enqueue(object : Callback<CountryApiResponse> {

                override fun onFailure(call: Call<CountryApiResponse>, t: Throwable) {
                    listener.requestInternetIssue()
                }

                override fun onResponse(
                    call: Call<CountryApiResponse>,
                    response: Response<CountryApiResponse>
                ) {
                    if (response.isSuccessful && response.code() == 200) {
                        response.body()?.let { listener.requestCountryApiResponse(it) }
                    }
                }
            })


    }

    override fun onFailure(call: Call<CurrencyApiResponse>, t: Throwable) {
        listener.requestInternetIssue()
    }

    override fun onResponse(
        call: Call<CurrencyApiResponse>,
        response: Response<CurrencyApiResponse>
    ) {
        if (response.isSuccessful && response.code() == 200) {
            response.body()?.let { listener.requestSuccess(it) }
        } else {
            response.body()?.let { listener.requestFailure() }
        }

    }


}



