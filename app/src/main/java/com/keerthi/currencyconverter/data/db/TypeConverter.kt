package com.keerthi.currencyconverter.data.db

import androidx.room.TypeConverter

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This T class is used to defined the table structure of AvailableCurrencies Table in database
 */
object TypeConverter {

    @TypeConverter
    @JvmStatic
    fun mapToString(value: Map<String, Double>?): String? {
        val builder = StringBuilder()
        value?.forEach {
            builder.append(it.key)
            builder.append("=")
            builder.append(it.value)
            builder.append(";")
        } ?: return null

        builder.setLength(builder.length - 1)
        return builder.toString()
    }

    @TypeConverter
    @JvmStatic
    fun mapToHashString(value: Map<String, String>?): String? {
        val builder = StringBuilder()
        value?.forEach {
            builder.append(it.key)
            builder.append("=")
            builder.append(it.value)
            builder.append(";")
        } ?: return null

        builder.setLength(builder.length - 1)
        return builder.toString()
    }

    @TypeConverter
    @JvmStatic
    fun stringToHashMap(value: String?): Map<String, String>? {
        return value?.split(";")?.associate {
            val (key, value) = it.split("=")
            key to value
        }
    }

    @TypeConverter
    @JvmStatic
    fun stringToMap(value: String?): Map<String, Double>? {
        return value?.split(";")?.associate {
            val (key, floatValue) = it.split("=")
            key to floatValue.toDouble()
        }
    }

}