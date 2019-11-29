package com.keerthi.currencyconverter.viewmodel

import com.keerthi.currencyconverter.model.ExchangeCurrency

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This interface listerner is used to transfer the data between presenter and view
 *
 */
interface CurrencyListener {

    fun postCurrencyData(currencyData: ExchangeCurrency)

    fun showErrorMessage(errorMsg: String)

    fun showInternetMessage()

    fun showLoader(status: Boolean)
}