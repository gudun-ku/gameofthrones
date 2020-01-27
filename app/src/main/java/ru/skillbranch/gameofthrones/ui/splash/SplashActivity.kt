package ru.skillbranch.gameofthrones.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.internal.operators.observable.ObservableTimer
import io.reactivex.schedulers.Schedulers
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.ui.main.MainActivity
import java.util.concurrent.TimeUnit


class SplashActivity : AppCompatActivity() {

    val sm: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        val source =
//            Observable.just("Testing", "One", "Two", "Three")
//        source.subscribe { s: String ->
//            Log.e(
//                "SplashActivity",
//                "RECEIVED: $s"
//            )
//        }


        observableStartMainActivity()
    }

    private fun observableStartMainActivity() {
        val timer1 = ObservableTimer(AppConfig.SPLASH_TIME_OUT *5, TimeUnit.SECONDS, Schedulers.single())
        val timer2 = ObservableTimer(6L * 10, TimeUnit.SECONDS , Schedulers.single())

        Observable.merge(
            timer1,
            timer2)
            .take(2)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                startMainActivity()
            }

    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        startColorChanging()
    }

    override fun onStop() {
        super.onStop()
        sm.clear()
    }

    private fun startColorChanging() {

       val colorObserver = Observable.range(1,4)
           .concatMap { value -> Observable.just(value).delay(700, TimeUnit.MILLISECONDS)}
           .repeat()
           .subscribeOn(Schedulers.newThread())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe { changeColor(it) }
        sm.add(colorObserver)

    }

    private fun changeColor(value: Int) {
        Log.d("SplashActivity", "changeColor: $value")
    }

    enum class AdditionalColor(val color: Triple<Int,Int,Int>) {
        NORMAL(Triple(255,255,255)),
        WARNING(Triple(255,120,0)),
        DANGER(Triple(255,60,60)),
        CRITICAL(Triple(255,0,0));

        fun nextColor(): AdditionalColor {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

}
