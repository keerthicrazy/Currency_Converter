package com.keerthi.currencyconverter.model

import java.util.*

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This model class is used to hold list of currency details with source and last updated time.
 */
data class ExchangeCurrency(

    var sourceCurrency: String,

    var lastUpdatedTime: String,

    var currencyList: ArrayList<ExtCurrencyDetail>
)