package ru.skillbranch.gameofthrones

import android.app.Application
import android.content.Context
import android.widget.Toast

class GOTApplication: Application() {

    lateinit var gToast: Toast

    fun showToast(sMessage: String, context: Context) {
        if (::gToast.isInitialized) {
           gToast.cancel()
        }

        gToast = Toast.makeText(context, sMessage, Toast.LENGTH_LONG)
        gToast.show()
    }

}