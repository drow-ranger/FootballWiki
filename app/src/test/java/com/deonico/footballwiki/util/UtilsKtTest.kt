package com.deonico.footballwiki.util

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat

class UtilsKtTest {

    @Test
    fun changeFormatDate() {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")
        val date = dateFormat.parse("02/26/2018")
        assertEquals("Mon, 26 Feb 2018", changeFormatDate(date))
    }
}