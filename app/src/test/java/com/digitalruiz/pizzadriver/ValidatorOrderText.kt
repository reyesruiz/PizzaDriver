package com.digitalruiz.pizzadriver

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatorOrderText  {

    @Test
    fun whenOrderNumberIsValid(){
        val orderNumber = 100
        val result = Validator.validateOrderNumber(orderNumber)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun whenOrderNumberIsInvalid(){
        val orderNumber = -1
        val result = Validator.validateOrderNumber(orderNumber)
        assertThat(result).isEqualTo(false)
    }
}
