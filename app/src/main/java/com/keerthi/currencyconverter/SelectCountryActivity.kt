package com.keerthi.currencyconverter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.keerthi.currencyconverter.adapter.SelectCurrencyAdapter
import com.keerthi.currencyconverter.model.AvailableCountriesSingleton
import com.keerthi.currencyconverter.model.CountryCurrencyDetail
import com.keerthi.currencyconverter.utils.Constants.Companion.EXTRA_CURRENCY_CODE
import kotlinx.android.synthetic.main.activity_select_country.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This activity is used to display the list of currencies available
 * From This, User can select the currency
 */
class SelectCountryActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private var currrencies: ArrayList<CountryCurrencyDetail> = ArrayList()

    private lateinit var countryAdapter: SelectCurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_country)
        title = getString(R.string.select_currency)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        rv_select_country.layoutManager = LinearLayoutManager(this)
        rv_select_country.setHasFixedSize(true)

        currrencies = AvailableCountriesSingleton.getInstance().getCurrencyList()
        countryAdapter = SelectCurrencyAdapter()
        rv_select_country.adapter = countryAdapter

        loadData(currrencies)


        countryAdapter.setOnItemClickListener(object : SelectCurrencyAdapter.OnItemClickListener {

            override fun onItemClick(currDetail: CountryCurrencyDetail) {
                val data = Intent().apply {
                    putExtra(EXTRA_CURRENCY_CODE, currDetail.currencyCode)
                }
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.getActionView() as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextChange(query: String): Boolean {
        loadData(filter(query.toLowerCase(Locale.US)))
        return false
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    fun loadData(currrencies: ArrayList<CountryCurrencyDetail>) {
        if (currrencies.size > 0) {
            rv_select_country.visibility = View.VISIBLE
            tv_no_country.visibility = View.GONE
            countryAdapter.loadAvailableCurrencyData(currrencies)
        } else {
            rv_select_country.visibility = View.GONE
            tv_no_country.visibility = View.VISIBLE
            tv_no_country.text = getString(R.string.no_currency_found)
        }
    }

    private fun filter(query: String): ArrayList<CountryCurrencyDetail> {

        var filtered: ArrayList<CountryCurrencyDetail> = ArrayList();


        for (c: CountryCurrencyDetail in this.currrencies) {
            if (c.currencyCode.toLowerCase(Locale.US).contains((query)) ||
                c.currencyName.toLowerCase(Locale.US).contains((query))
            ) {
                filtered.add(c)
            }
        }
        return filtered
    }


}
