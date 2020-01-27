package ru.skillbranch.gameofthrones.ui.main

import android.app.Activity
import android.os.Bundle
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.repositories.RootRepository

class MainActivity : Activity() {


    val repository = RootRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun loadData() {
//        val data = repository.getNeedHouseWithCharacters(*AppConfig.NEED_HOUSES)
//        val houses = mutableListOf<HouseRes>()
//        val character = mutableListOf<CharacterRes>()
//        data.forEach { (house, character) ->
//            houses.add(house)
//            character.addAll(characters.map {
//                characterResponse.houseId = house.name.toShortName()
//            })
//        }
//        repository.insertHouses(houses)
//        repository.insertCharacters(character)
//        withContext(Dispatchers.Main) {
//            router.goToCharactersList()
//        }

    }

}
