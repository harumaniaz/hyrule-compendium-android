package com.example.hyrulecompendium.data

import com.squareup.moshi.Json

data class EntriesDataResponse(
    @field:Json(name = "data")
    val data: List<Entry>
)

data class Entry(
    // Common
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "category")
    val category: String,
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "image")
    val image: String,
    @field:Json(name = "common_locations")
    val commonLocations: List<String>? = null,
    @field:Json(name = "dlc")
    val dlc: Boolean,

    // By category
    @field:Json(name = "drops")
    val drops: List<String>? = null,
    @field:Json(name = "properties")
    val properties: Properties? = null,
    @field:Json(name = "hearts_recovered")
    val heartsRecovered: Float? = null,
    @field:Json(name = "cooking_effect")
    val cookingEffect: String? = null,
    @field:Json(name = "edible")
    val edible: Boolean? = null,

    // "Tears of the Kingdom" Only
    @field:Json(name = "fuse_attack_power")
    val fuseAttackPower: Int? = null,
)

data class Properties(
    @field:Json(name = "attack")
    val attack: Int? = null,
    @field:Json(name = "defense")
    val defense: Int? = null,

    // "Tears of the Kingdom" Only
    @field:Json(name = "effect")
    val effect: String? = null,
    @field:Json(name = "type")
    val type: String? = null,
)