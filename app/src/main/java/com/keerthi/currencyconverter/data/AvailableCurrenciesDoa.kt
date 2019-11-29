package com.keerthi.currencyconverter.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This doa class is used to defined the allowed methods to access AvailableCurrencies Table in Database
 */
@Dao
interface AvailableCurrenciesDoa {

    @Insert
    fun insert(availableCurrencies: AvailableCurrencies)

    @Query("SELECT * FROM AvailableCurrencies ORDER BY id DESC LIMIT 1")
    fun fetchAvailableCountries(): AvailableCurrencies

}