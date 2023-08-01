package com.example.hyrulecompendium.ui.screen.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.hyrulecompendium.R
import com.example.hyrulecompendium.data.CategoryType
import com.example.hyrulecompendium.data.Entry
import com.example.hyrulecompendium.data.GameType
import com.example.hyrulecompendium.ui.component.CategoryFilterGroup
import com.example.hyrulecompendium.ui.component.EntryRow
import com.example.hyrulecompendium.ui.component.HcScaffold
import com.example.hyrulecompendium.ui.component.HcTopBar
import com.example.hyrulecompendium.ui.component.SimpleAlertDialog
import com.example.hyrulecompendium.ui.navigation.ScreenRoute
import com.example.hyrulecompendium.ui.theme.HcTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val uiEvent by viewModel.uiEvent.collectAsState(initial = HomeViewModel.UiEvent.None())

    HomeContent(
        uiState = uiState,
        uiEvent = uiEvent,
        onNavigateToDetail = {
            navController.navigate(ScreenRoute.detail(it.toString()))
        },
        onClickCategory = viewModel::setCategoryFilter
    )
}

@Composable
private fun HomeContent(
    uiState: HomeViewModel.UiState,
    uiEvent: HomeViewModel.UiEvent,
    onNavigateToDetail: (Int) -> Unit = {},
    onClickCategory: (CategoryType) -> Unit = {},
) {
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val listState = rememberLazyListState()
    val context = LocalContext.current

    LaunchedEffect(uiEvent) {
        when (uiEvent) {
            is HomeViewModel.UiEvent.FetchError -> {
                errorMessage = context.getString(R.string.dialog_message_fetch_error)
            }

            is HomeViewModel.UiEvent.CategoryChanged -> {
                listState.scrollToItem(index = 0)
            }

            else -> {}
        }
    }

    HcScaffold(
        topBar = {
            HcTopBar(
                title = stringResource(id = GameType.BOTW.titleId),
                // TODO: Search or Settings(e.g. switch to "botw" or "totk")
//                actionIcon = Icons.Outlined.Settings,
//                onActionClick = {}
            )
        },
        isLoading = uiState.isLoading
    ) {
        CategoryFilterGroup(
            categories = uiState.categories,
            selectedItem = uiState.selectedCategory,
            onClickItem = onClickCategory
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                items = uiState.entries,
                key = { item -> item.id }
            ) { entry ->
                EntryRow(
                    entry = entry,
                    onClick = { onNavigateToDetail(entry.id) }
                )
            }
        }
    }

    errorMessage?.let {
        SimpleAlertDialog(
            message = it,
            confirmButtonText = stringResource(id = R.string.dialog_button_ok),
            onConfirmClick = { errorMessage = null })
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    GameType.initialize(LocalContext.current)
    CategoryType.initialize(LocalContext.current)

    val uiState = HomeViewModel.UiState(
        entries = listOf(
            Entry(
                id = 203,
                name = "apple",
                category = "materials",
                description = "A common fruit eaten by people since ancient times.",
                image = "https://botw-compendium.herokuapp.com/api/v3/compendium/entry/apple/image",
                dlc = false
            ),
            Entry(
                id = 1,
                name = "horse",
                category = "creatures",
                description = "These can most often be found on plains.",
                image = "https://botw-compendium.herokuapp.com/api/v3/compendium/entry/horse/image",
                dlc = false
            ),
            Entry(
                id = 147,
                name = "lynel",
                category = "monsters",
                description = "These fearsome monsters have lived in Hyrule since ancient times.",
                image = "https://botw-compendium.herokuapp.com/api/v3/compendium/entry/lynel/image",
                dlc = false
            )
        )
    )

    HcTheme {
        HomeContent(
            uiState = uiState,
            uiEvent = HomeViewModel.UiEvent.None()
        )
    }
}
