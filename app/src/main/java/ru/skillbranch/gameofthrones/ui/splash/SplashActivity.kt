package ru.skillbranch.gameofthrones.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.internal.operators.observable.ObservableTimer
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.ui.main.MainActivity
import java.util.concurrent.TimeUnit


class SplashActivity : AppCompatActivity() {

    lateinit var toast: Toast

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        Observables.zip(
        ObservableTimer(AppConfig.SPLASH_TIME_OUT, TimeUnit.SECONDS, Schedulers.newThread()),
        ObservableTimer(6L, TimeUnit.SECONDS, Schedulers.newThread()))
            .subscribeBy(
                onComplete = { startMainActivity() },
                onError = { workWitError() }
            ).dispose()
    }

    private fun startMainActivity() {
        val i = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    fun workWitError() {
        showToast("Error has happened!")
    }

    fun showToast(text: String) {
        if (toast != null)
            toast.cancel()
        toast = Toast.makeText(applicationContext, text, Toast.LENGTH_LONG)
        toast.show()
    }
}
