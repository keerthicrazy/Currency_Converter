package com.keerthi.currencyconverter.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This Entity class is used to defined the table structure of AvailableCurrencies Table in database
 * Also, records storing and fetching to be done via Object table mapping
 */
@Entity(tableName = "AvailableCurrencies")
data class AvailableCurrencies(
    val aCountries: Map<String, String>
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0;
}
