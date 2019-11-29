package com.keerthi.currencyconverter.network

import com.keerthi.currencyconverter.model.CountryApiResponse
import com.keerthi.currencyconverter.model.CurrencyApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This Interface is used for define the api calls in retrofit.
 */
interface CurrencyApiService {

    @GET("api/live")
    fun getCurrencyLiveData(
        @Query("access_key") accessKey: String,
        @Query("format") formatType: Int
    ): Call<CurrencyApiResponse>

    @GET("api/list")
    fun getAvailableCurrencies(@Query("access_key") accessKey: String): Call<CountryApiResponse>


}