package com.example.hyrulecompendium.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.hyrulecompendium.ui.theme.AccentColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HcScaffold(
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    isLoading: Boolean = false,
    content: @Composable () -> Unit = {},
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = topBar,
            bottomBar = bottomBar
        ) { padding ->
            Column(modifier = Modifier.padding(padding)) {
                content()
            }
        }

        if (isLoading) {
            LoadingBar()
        }
    }
}

@Composable
fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(color = AccentColor)
    }
}