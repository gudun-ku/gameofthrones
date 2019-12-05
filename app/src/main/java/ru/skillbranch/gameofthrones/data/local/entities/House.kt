package ru.skillbranch.gameofthrones.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.skillbranch.gameofthrones.repositories.Converters

@Entity(tableName = "houses")
data class House(
    @PrimaryKey
    val id: String,
    val name: String,
    val region: String,
    val coatOfArms: String,
    val words: String,
    val titles: List<String>,
    val seats: List<String>,
    val currentLord: String, //rel
    val heir: String, //rel
    val overlord: String,
    val founded: String,
    val founder: String, //rel
    val diedOut: String,
    val ancestralWeapons: List<String>
)