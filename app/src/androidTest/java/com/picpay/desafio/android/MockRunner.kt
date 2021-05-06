package com.picpay.desafio.android

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class MockRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?,
                                context: Context?): Application {
        return super.newApplication(cl, PicPayAppTest::class.java.name, context)
    }
}