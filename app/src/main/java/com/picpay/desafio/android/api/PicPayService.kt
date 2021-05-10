package com.picpay.desafio.android.api

import com.picpay.desafio.android.data.User
import retrofit2.Call
import retrofit2.http.GET

/**
 * Usado para conectar com a API da PicPay e retornar os usuários
 */
interface PicPayService {

    @GET("users")
    fun getUsers(): Call<List<User>>
}