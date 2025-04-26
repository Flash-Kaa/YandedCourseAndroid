package com.flasska.yndurfu.presentation.edit

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
internal object NextDaysSelectable : SelectableDates {

    val initialSelectedDateMillis: Long
        get() = now.plusDays(1)
            .atStartOfDay()
            .atZone(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()

    private val now: LocalDate
        get() = LocalDate.now(ZoneOffset.UTC)

    override fun isSelectableYear(year: Int) = now.year <= year

    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return now.plusDays(1)
            .atStartOfDay()
            .atZone(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli() <= utcTimeMillis
    }
}
