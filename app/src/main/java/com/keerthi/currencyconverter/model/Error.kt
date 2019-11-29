package com.keerthi.currencyconverter.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This model class is used to hold the error case in API
 */
data class Error(

    @Expose
    @SerializedName("code")
    var code: String,

    @Expose
    @SerializedName("type")
    var type: String,

    @Expose
    @SerializedName("info")
    var info: String
)