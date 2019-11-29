package com.keerthi.currencyconverter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.keerthi.currencyconverter.R
import com.keerthi.currencyconverter.model.CountryCurrencyDetail
import kotlinx.android.synthetic.main.item_select_currency.view.*

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This Adapter class is used to load the data in SelectCurrency RecyclerView
 */
class SelectCurrencyAdapter : RecyclerView.Adapter<SelectCurrencyAdapter.ViewHolder>() {

    private var currencies: ArrayList<CountryCurrencyDetail> = ArrayList()

    private var listener: OnItemClickListener? = null

    override fun getItemCount(): Int {
        return currencies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val extCurrency = currencies.get(position)
        holder.currencyCode.text = extCurrency.currencyCode
        holder.currencyName.text = extCurrency.currencyName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_select_currency, parent, false)
        return ViewHolder(itemView)
    }

    fun loadAvailableCurrencyData(currency: ArrayList<CountryCurrencyDetail>) {
        this.currencies = currency
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(currencies.get(position))
                }
            }
        }


        var currencyCode: TextView = itemView.tv_curr_code
        var currencyName: TextView = itemView.tv_curr_name
    }

    interface OnItemClickListener {
        fun onItemClick(currDetail: CountryCurrencyDetail)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}