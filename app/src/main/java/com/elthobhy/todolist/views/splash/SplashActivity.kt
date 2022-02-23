package com.elthobhy.todolist.views.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.elthobhy.todolist.R
import com.elthobhy.todolist.views.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
        },3000)
    }
}