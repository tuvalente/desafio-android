package com.picpay.desafio.android

class PicPayAppTest : PicPayApp() {

    override fun getUrl(): String {
        return "http://127.0.0.1:8080"
    }
}