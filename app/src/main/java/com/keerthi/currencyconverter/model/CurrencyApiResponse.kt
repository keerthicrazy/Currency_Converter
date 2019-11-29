package com.keerthi.currencyconverter.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This model is used to receive the Live Currency API Response
 */
data class CurrencyApiResponse(

    @Expose
    @SerializedName("success")
    var succuss: Boolean,

    @Expose
    @SerializedName("terms")
    var terms: String,

    @Expose
    @SerializedName("privacy")
    var privacy: String,

    @Expose
    @SerializedName("timestamp")
    var timestamp: Long,

    @Expose
    @SerializedName("source")
    var source: String,

    @Expose
    @SerializedName("quotes")
    var quotes: Map<String, Double>,

    @Expose
    @SerializedName("error")
    var error: Error
)