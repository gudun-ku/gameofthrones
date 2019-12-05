package ru.skillbranch.gameofthrones.data.remote.api


import ru.skillbranch.gameofthrones.AppConfig.BASE_URL
import ru.skillbranch.gameofthrones.data.remote.util.RetrofitFactory

object GoTApiFactory {
    private val retrofitClient = RetrofitFactory.getApiClient(BASE_URL)
    var gotApi = retrofitClient!!.create(GoTApi::class.java)
       get() = gotApi
}