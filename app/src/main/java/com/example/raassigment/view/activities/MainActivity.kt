package com.example.raassigment.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.raassigment.R
import com.example.raassigment.application.Application
import com.example.raassigment.databinding.ActivityMainBinding
import com.example.raassigment.di.ActivityComponent
import com.example.raassigment.viewmodel.FacilityViewModel
import com.example.raassigment.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null

    private val binding
    get() = _binding!!

    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent = (application as Application)
            .applicationComponent.activityComponent().create()

        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}