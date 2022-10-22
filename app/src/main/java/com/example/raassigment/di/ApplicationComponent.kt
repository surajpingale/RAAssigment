package com.example.raassigment.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class, Subcomponents::class])
interface ApplicationComponent {

    fun activityComponent() : ActivityComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context) : ApplicationComponent
    }

}