package com.digitalruiz.pizzadriver

object Validator  {
    fun validateOrderNumber(orderNumber: Int): Boolean {
        return orderNumber >= 0
    }
}