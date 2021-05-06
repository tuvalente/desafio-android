package com.picpay.desafio.android

import android.app.Application

open class PicPayApp : Application() {
    open fun getUrl() = "http://careers.picpay.com/tests/mobdev/"
}