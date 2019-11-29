package com.keerthi.currencyconverter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.keerthi.currencyconverter.R
import com.keerthi.currencyconverter.model.ExtCurrencyDetail
import com.keerthi.currencyconverter.utils.CurrencyUtils
import kotlinx.android.synthetic.main.item_country_currency.view.*

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This Adapter class is used to load the data in Currency RecyclerView
 */
class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    private var currencies: ArrayList<ExtCurrencyDetail> = ArrayList()

    override fun getItemCount(): Int {
        return currencies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val extCurrency = currencies.get(position)
        holder.currencyCode.text = extCurrency.currencyCode
        holder.currencyName.text = extCurrency.currencyName
        holder.currencyValue.text = CurrencyUtils.roundToTwoDecimalPlaces(extCurrency.currencyValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country_currency, parent, false)
        return ViewHolder(itemView)
    }

    fun loadCurrencyData(currency: ArrayList<ExtCurrencyDetail>) {
        this.currencies = currency
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var currencyCode: TextView = itemView.tv_currency_code
        var currencyName: TextView = itemView.tv_currency_name
        var currencyValue: TextView = itemView.tv_currency_value

    }
}