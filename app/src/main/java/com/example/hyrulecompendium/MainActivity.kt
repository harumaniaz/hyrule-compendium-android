package com.example.hyrulecompendium

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.hyrulecompendium.data.CategoryType
import com.example.hyrulecompendium.data.GameType
import com.example.hyrulecompendium.ui.navigation.ScreenRoute
import com.example.hyrulecompendium.ui.navigation.SetupNavGraph
import com.example.hyrulecompendium.ui.theme.HcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GameType.initialize(this)
        CategoryType.initialize(this)

        setContent {
            HcTheme {
                val navController = rememberNavController()
                SetupNavGraph(
                    navController = navController,
                    startDestination = ScreenRoute.home()
                )
            }
        }
    }
}