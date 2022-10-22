package com.example.raassigment.application

import android.app.Application
import com.example.raassigment.di.ApplicationComponent
import com.example.raassigment.di.DaggerApplicationComponent

class Application : Application() {

    lateinit var applicationComponent: ApplicationComponent


    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }

}
