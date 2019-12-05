package ru.skillbranch.gameofthrones.data.remote.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

interface GoTApi {
    @GET("houses?pageSize=50")
    suspend fun getAllHouses(@Query("page") pageNumber: Int)
            : Deferred<Response<List<HouseRes>>>
}