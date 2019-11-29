package com.keerthi.currencyconverter.data

import android.app.Application
import android.os.AsyncTask
import com.keerthi.currencyconverter.data.db.CurrencyDatabase

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This Repository class is used to fetch and store the currency data in database
 */
class CurrencyReporsitory(application: Application) {

    private var currencyDoa: CurrencyDoa

    private var availableCurrenciesDoa: AvailableCurrenciesDoa

    init {

        val database: CurrencyDatabase =
            CurrencyDatabase.getInstance(application.applicationContext)!!
        currencyDoa = database.currencyDoa()
        availableCurrenciesDoa = database.availableCountriesDoa()
    }

    fun insertAvailableCountries(availableCurrencies: AvailableCurrencies) {
        InsertAvailableCurrenciesAsyncTask(availableCurrenciesDoa).execute(availableCurrencies)
    }

    fun fetchAvailableCurrencies(): AvailableCurrencies? {
        return FetchAvailableCurrenciesAsyncTask(availableCurrenciesDoa).execute().get()
    }

    fun insert(currency: Currency) {
        InsertCurrencyAsyncTask(currencyDoa).execute(currency)
    }

    fun getLastCurrencyData(): Currency? {
        return GetLastCurrencyAsyncTask(currencyDoa).execute().get();
    }

    companion object {

        private class InsertAvailableCurrenciesAsyncTask(availableCurrenciesDoa: AvailableCurrenciesDoa) :
            AsyncTask<AvailableCurrencies, Unit, Unit>() {

            val availableCurrenciesDoa = availableCurrenciesDoa;

            override fun doInBackground(vararg p0: AvailableCurrencies?) {
                availableCurrenciesDoa.insert(p0[0]!!)
            }
        }

        private class FetchAvailableCurrenciesAsyncTask(availableCurrenciesDoa: AvailableCurrenciesDoa) :
            AsyncTask<Unit, Unit, AvailableCurrencies?>() {

            val availableCurrenciesDoa = availableCurrenciesDoa;

            override fun doInBackground(vararg p0: Unit?): AvailableCurrencies {
                return availableCurrenciesDoa.fetchAvailableCountries()
            }
        }


        private class InsertCurrencyAsyncTask(currencyDoa: CurrencyDoa) :
            AsyncTask<Currency, Unit, Unit>() {

            val currencyDoa = currencyDoa;

            override fun doInBackground(vararg p0: Currency?) {
                currencyDoa.insert(p0[0]!!)
            }
        }

        private class GetLastCurrencyAsyncTask(currencyDoa: CurrencyDoa) :
            AsyncTask<Unit, Unit, Currency?>() {

            val currencyDoa = currencyDoa

            override fun doInBackground(vararg p0: Unit?): Currency {
                return currencyDoa.fetchLastRecord()
            }
        }
    }
}