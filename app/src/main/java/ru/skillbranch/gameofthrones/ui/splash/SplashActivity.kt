package ru.skillbranch.gameofthrones.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.skillbranch.gameofthrones.ui.main.MainActivity
import android.content.Intent


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }
}
