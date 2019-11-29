package com.keerthi.currencyconverter.model

import com.keerthi.currencyconverter.data.AvailableCurrencies

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This singleton class is used to hold the available currencies
 *
 * whenever select currency is selected, data will be fetched from this class
 */
class AvailableCountriesSingleton {

    var availableCountries: AvailableCurrencies? = null

    private var currencyList: ArrayList<CountryCurrencyDetail> = ArrayList();

    companion object {

        private var instance: AvailableCountriesSingleton? = null

        fun getInstance(): AvailableCountriesSingleton {

            if (instance == null) {
                instance = AvailableCountriesSingleton()
            }

            return instance as AvailableCountriesSingleton

        }
    }

    fun getCurrencyList(): ArrayList<CountryCurrencyDetail> {

        if (currencyList.size < 1) {

            for (entry in availableCountries!!.aCountries.entries) {

                val countryCurrencyDetail = CountryCurrencyDetail(
                    entry.key, entry.value
                )
                currencyList.add(countryCurrencyDetail)
            }
        }

        return currencyList
    }

    fun getCurrencyName(currencyCode: String): String? {
        return availableCountries!!.aCountries.get(currencyCode);
    }


}