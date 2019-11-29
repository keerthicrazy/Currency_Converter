package com.keerthi.currencyconverter.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.keerthi.currencyconverter.data.AvailableCurrencies
import com.keerthi.currencyconverter.data.AvailableCurrenciesDoa
import com.keerthi.currencyconverter.data.Currency
import com.keerthi.currencyconverter.data.CurrencyDoa

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This abstract database is used create the database and access the doa from instance
 */
@Database(entities = [Currency::class, AvailableCurrencies::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDoa(): CurrencyDoa

    abstract fun availableCountriesDoa(): AvailableCurrenciesDoa

    companion object {

        private var instance: CurrencyDatabase? = null

        fun getInstance(context: Context): CurrencyDatabase? {

            if (instance == null) {
                synchronized(CurrencyDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CurrencyDatabase::class.java, "currency_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build();

                }
            }

            return instance;
        }

    }
}