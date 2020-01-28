package ru.skillbranch.gameofthrones.ui.splash

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.operators.observable.ObservableTimer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.GOTApplication
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.ui.main.MainActivity
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    val sm: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        swipeContainer.setOnRefreshListener {
            restart()
        }

        restart()
    }

    private fun restart() {
        sm.clear()
        startTimeout()
        startApp()
        startProgressColorChanging()
        swipeContainer.isRefreshing = false
    }

    private fun startTimeout() {
        val errorTimeoutTimer =
            ObservableTimer(AppConfig.SPLASH_TIME_OUT, TimeUnit.SECONDS, Schedulers.single())
            .map { Throwable("Timeout Error!") }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { showError(it.message)}
        sm.add(errorTimeoutTimer)
    }

    private fun startApp() {
        val startAppTimer =
            ObservableTimer(AppConfig.SPLASH_TIME_OUT - 1, TimeUnit.SECONDS, Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { startMainActivity() }
        sm.add(startAppTimer)
    }

    private fun showError(msg: String?) {
        var message = msg
        if (message.isNullOrEmpty()) {
            message = "Just another error"
        }
        (application as GOTApplication).showToast(message, context = this)
        sm.clear()

    }

    private fun startMainActivity() {
        sm.clear()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun onStop() {
        super.onStop()
        sm.clear()
    }

    private fun startProgressColorChanging() {

       val colorObserver = Observable.range(0,6)
           .concatMap { value -> Observable.just(value).delay(200, TimeUnit.MILLISECONDS)}
           .repeat()
           .subscribeOn(Schedulers.newThread())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe { changeColor(AdditionalColor.NORMAL, it) }
        sm.add(colorObserver)

    }

    private fun changeColor(color: AdditionalColor, value: Int) {
        Log.d("SplashActivity", "changeColor: $value")
        val vColor = color.getColor(value)
        val (r,g, b) = vColor.color
        IvSplash.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
    }

    enum class AdditionalColor(val color: Triple<Int,Int,Int>) {
        NORMAL(Triple(255,255,255)),
        LIGHTWARNING(Triple(255,180,120)),
        PREWARNING(Triple(255,120,60)),
        WARNING(Triple(255,120,0)),
        DANGER(Triple(255,60,60)),
        PRECRITICAL(Triple(255,60,0)),
        CRITICAL(Triple(255,0,0));

        fun nextColor(): AdditionalColor {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }

        fun getColor(vColor: Int): AdditionalColor {
            return if (vColor < values().lastIndex) {
                values()[vColor]
            } else {
                values()[0]
            }
        }
    }

}
