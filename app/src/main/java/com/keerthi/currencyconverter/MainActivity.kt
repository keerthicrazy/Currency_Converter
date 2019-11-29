package com.keerthi.currencyconverter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.keerthi.currencyconverter.adapter.CurrencyAdapter
import com.keerthi.currencyconverter.model.ExchangeCurrency
import com.keerthi.currencyconverter.utils.Constants
import com.keerthi.currencyconverter.viewmodel.CurrencyListener
import com.keerthi.currencyconverter.viewmodel.CurrencyViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This activity is used to get the input current and selected currency from the list of currencies available
 *
 */
class MainActivity : AppCompatActivity(), CurrencyListener {


    private lateinit var viewModel: CurrencyViewModel

    private lateinit var currencyAdapter: CurrencyAdapter

    private lateinit var srcCurrency: String

    private var srcCurrencyValue: Double = 1.0

    private var currencyData: ExchangeCurrency? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDefaults()
    }

    private fun initDefaults() {

        tv_src_currency.text = Constants.DEFAULT_SOURCE_CURRENCY
        tv_src_currency_value.setText("1")

        tv_src_currency_value.isCursorVisible = true
        tv_src_currency_value.setSelection(1)
        tv_src_currency_value.requestFocus()
        tv_src_currency_value.isClickable = false

        srcCurrency = Constants.DEFAULT_SOURCE_CURRENCY

        rv_currencies.layoutManager = LinearLayoutManager(this);
        rv_currencies.setHasFixedSize(true)

        currencyAdapter = CurrencyAdapter()
        rv_currencies.adapter = currencyAdapter

        viewModel = ViewModelProviders.of(this).get(CurrencyViewModel::class.java)
        viewModel.initListener(this)

        viewModel.getAvailableCurrencies(srcCurrency, srcCurrencyValue)


        tv_src_currency_value.setOnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                refreshCurrency()
                false
            } else {
                false
            }
        }

        tv_src_currency.setOnClickListener { view ->
            var intent = Intent(this, SelectCountryActivity::class.java)
            startActivityForResult(intent, Constants.SELECT_COUNTRY_REQUEST)
        }

        tv_src_currency_value.setOnClickListener { view ->
            tv_src_currency_value.isCursorVisible = true
            tv_src_currency_value.setSelection(tv_src_currency_value.text!!.length)
        }

    }

    fun refreshCurrency() {
        srcCurrency = tv_src_currency.text.toString()
        srcCurrencyValue = tv_src_currency_value.text.toString().toDouble()
        viewModel.getCurrency(srcCurrency, srcCurrencyValue)
    }

    override fun postCurrencyData(currencyData: ExchangeCurrency) {

        if (!isFinishing) {
            val lastUpdatedTime = "Last updated Time " + currencyData.lastUpdatedTime
            this.currencyData = currencyData
            currencyAdapter.loadCurrencyData(currencyData.currencyList)
            tv_last_updated.text = lastUpdatedTime
            tv_error_msg.visibility = View.GONE
        }

    }

    override fun showErrorMessage(errorMsg: String) {
        if (!isFinishing) {
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        }
    }

    override fun showInternetMessage() {
        if (!isFinishing) {
            Toast.makeText(this, this.getString(R.string.internet_connection), Toast.LENGTH_LONG)
                .show();
        }
    }

    override fun showLoader(status: Boolean) {
        if (!isFinishing) {
            if (status) {
                loader.visibility = View.VISIBLE
            } else {
                loader.visibility = View.GONE
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.SELECT_COUNTRY_REQUEST && resultCode == Activity.RESULT_OK) {
            tv_src_currency.setText(data!!.getStringExtra(Constants.EXTRA_CURRENCY_CODE))
            refreshCurrency()
        }
    }


}
