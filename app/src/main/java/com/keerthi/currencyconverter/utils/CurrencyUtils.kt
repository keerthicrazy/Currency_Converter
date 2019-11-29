package com.keerthi.currencyconverter.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Keerthivasan on 29/11/2019
 *
 * This common class used to utils and manipulate the data for conversion
 *
 */
class CurrencyUtils {

    companion object {

        fun convertTimeStampToDate(value: Long?): Date? {
            return value?.let { Date(it) }
        }

        fun roundToTwoDecimalPlaces(value: Double): String {
            val returnVal = value.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()

            val format = DecimalFormat("#,###.##")
            return format.format(returnVal)
        }

        fun convertTimeStamp(value: Long?): String? {
            val f = SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.US)
            return value?.let {
                f.format(Date(it))

            }
        }

    }
}