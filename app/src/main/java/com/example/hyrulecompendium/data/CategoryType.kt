package com.example.hyrulecompendium.data

import android.content.Context
import com.example.hyrulecompendium.R

enum class CategoryType(val id: String, val labelId: Int) {
    CREATURES("creatures", R.string.category_creatures),
    MONSTERS("monsters", R.string.category_monsters),
    MATERIALS("materials", R.string.category_materials),
    EQUIPMENT("equipment", R.string.category_equipment),
    TREASURES("treasure", R.string.category_treasure);

    lateinit var label: String
        private set

    companion object {
        fun initialize(context: Context) {
            CategoryType.values().forEach { it.label = context.getString(it.labelId) }
        }
    }
}