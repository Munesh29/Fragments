package com.example.task1fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState==null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragment, Fragment1())
                commit()
            }
        }



    }


}