package com.keerthi.currencyconverter.network

import com.keerthi.currencyconverter.model.CountryApiResponse
import com.keerthi.currencyconverter.model.CurrencyApiResponse

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This Interface is used to transfer the data from API Service to View Model
 */
interface ResponseRetrofit {

    fun requestSuccess(data: CurrencyApiResponse)

    fun requestCountryApiResponse(data: CountryApiResponse)

    fun requestFailure()

    fun requestInternetIssue()
}