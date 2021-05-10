package com.picpay.desafio.android.data

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UserTest {

    private lateinit var user : User

    @Before
    fun setUp() {
        user = User("", "Eduardo Santos", 1, "@eduardo.santos")
    }

    @Test
    fun test_default() {
        Assert.assertEquals("", user.img)
    }

    @Test
    fun test_hasImage() {
        Assert.assertEquals(false, user.hasImage())
    }

    @Test
    fun test_toString() {
        Assert.assertEquals("Eduardo Santos", user.toString())
    }
}
