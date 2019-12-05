package ru.skillbranch.gameofthrones.data.remote

import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import ru.skillbranch.gameofthrones.data.remote.api.GoTApi
import ru.skillbranch.gameofthrones.data.remote.api.GoTApiFactory
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

class RemoteStorage:BaseStorage() {

    val api: GoTApi = GoTApiFactory.gotApi

    fun getAllHouses(pageNumber: Int):MutableList<HouseRes>? {
        val houseResponse = GlobalScope.async(IO) {
            val houseRes =  getAllHousesAsync(pageNumber)
            houseRes
        }
        return houseResponse.getCompleted()
    }

    suspend fun getAllHousesAsync(pageNumber: Int):MutableList<HouseRes>? {
       val houseResponse = safeApiCall(
           call = {api.getAllHouses(pageNumber).await()},
           errorMessage =  "Error fetching houses!"
       )
       return houseResponse?.toMutableList()
    }
}