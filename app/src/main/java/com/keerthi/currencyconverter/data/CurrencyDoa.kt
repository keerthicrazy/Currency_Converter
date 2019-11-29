package com.keerthi.currencyconverter.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This doa class is used to defined the allowed methods to access Currency Table in Database
 */
@Dao
interface CurrencyDoa {

    @Insert
    fun insert(currency: Currency)

    @Query("SELECT * FROM currencies ORDER BY id DESC LIMIT 1")
    fun fetchLastRecord(): Currency
}