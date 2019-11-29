package com.keerthi.currencyconverter.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This model is used to receive the response of avaialble currency list.
 */
data class CountryApiResponse(

    @Expose
    @SerializedName("success")
    var success: Boolean,

    @Expose
    @SerializedName("terms")
    var terms: String,

    @Expose
    @SerializedName("privacy")
    var privacy: String,

    @Expose
    @SerializedName("currencies")
    var currencies: Map<String, String>,

    @Expose
    @SerializedName("error")
    var error: Error
)