package com.example.hyrulecompendium.data

import android.content.Context
import com.example.hyrulecompendium.R

enum class GameType(val id: Int, val titleId: Int) {
    BOTW(1, R.string.game_title_botw), // Breath of the Wild
    TOTK(2, R.string.game_title_totk); // Tears of the Kingdom

    lateinit var title: String
        private set

    companion object {
        fun initialize(context: Context) {
            GameType.values().forEach { it.title = context.getString(it.titleId) }
        }

        fun from(value: Int): GameType =
            GameType.values().firstOrNull { it.id == value } ?: BOTW
    }
}