package com.example.raassigment.di

import com.example.raassigment.view.activities.MainActivity
import com.example.raassigment.view.fragments.MainFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create() : ActivityComponent
    }

}