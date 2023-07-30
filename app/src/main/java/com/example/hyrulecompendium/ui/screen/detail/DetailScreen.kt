package com.example.hyrulecompendium.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.hyrulecompendium.R
import com.example.hyrulecompendium.data.CategoryType
import com.example.hyrulecompendium.data.remote.Entry
import com.example.hyrulecompendium.ui.component.HcScaffold
import com.example.hyrulecompendium.ui.component.HcTopBar
import com.example.hyrulecompendium.ui.theme.HcTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    navController: NavHostController,
    viewModel: DetailViewModel = koinViewModel(),
    id: Int
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getEntry(id)
    }

    DetailContent(
        uiState = uiState,
        navigateUp = {
            navController.navigateUp()
        }
    )
}

@Composable
private fun DetailContent(
    uiState: DetailUiState,
    navigateUp: () -> Unit = {}
) {
    HcScaffold(
        topBar = {
            HcTopBar(
                title = uiState.entry?.name ?: "",
                navigationIcon = Icons.Outlined.ArrowBack,
                onNavigationClick = navigateUp
            )
        }
    ) {
        uiState.entry?.let { entry ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .size(200.dp)
                ) {
                    AsyncImage(
                        model = entry.image,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Text(
                    text = entry.description,
                    modifier = Modifier.padding(start = 40.dp, end = 40.dp, bottom = 20.dp)
                )

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.texture),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Common Locations
                        MultipleValues(
                            list = entry.commonLocations,
                            labelId = R.string.label_common_locations,
                            emptyId = R.string.value_no_common_locations
                        )

                        Spacer(modifier = Modifier.height(30.dp))

                        when (entry.category) {
                            CategoryType.CREATURES.id -> CreaturesSelection(entry)
                            CategoryType.EQUIPMENT.id -> EquipmentSelection(entry)
                            CategoryType.MATERIALS.id -> MaterialsSelection(entry)
                            CategoryType.MONSTERS.id -> MonstersSelection(entry)
                            CategoryType.TREASURES.id -> TreasureSelection(entry)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CreaturesSelection(entry: Entry) {
    when (entry.edible) {
        true -> {
            // Cooking Effect
            SingleValue(
                value = entry.cookingEffect,
                labelId = R.string.label_cooking_effect
            )
        }

        else -> {
            // Droppable Items
            MultipleValues(
                list = entry.drops,
                labelId = R.string.label_droppable_items
            )
        }
    }
}

@Composable
private fun EquipmentSelection(entry: Entry) {
    val attack = entry.properties?.attack ?: 0
    val defense = entry.properties?.defense ?: 0

    when {
        attack > 0 -> {
            // Attack Power
            SingleValue(
                value = attack,
                labelId = R.string.label_attack_power
            )
        }

        defense > 0 -> {
            // Defense Power
            SingleValue(
                value = defense,
                labelId = R.string.label_defense_power
            )
        }

        else -> {
            // Attack Power
            SingleValue(
                value = stringResource(id = R.string.value_unknown),
                labelId = R.string.label_attack_power
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Defense Power
            SingleValue(
                value = stringResource(id = R.string.value_unknown),
                labelId = R.string.label_defense_power
            )
        }
    }
}

@Composable
private fun MaterialsSelection(entry: Entry) {
    // Cooking Effect
    SingleValue(
        value = entry.cookingEffect,
        labelId = R.string.label_cooking_effect
    )
}

@Composable
private fun MonstersSelection(entry: Entry) {
    // Droppable Items
    MultipleValues(
        list = entry.drops,
        labelId = R.string.label_droppable_items,
    )
}

@Composable
private fun TreasureSelection(entry: Entry) {
    // Droppable Items
    MultipleValues(
        list = entry.drops,
        labelId = R.string.label_droppable_items
    )
}

@Composable
private fun MultipleValues(list: List<String>?, labelId: Int, emptyId: Int = R.string.value_none) {
    LabelText(text = stringResource(id = labelId))
    when {
        list == null -> ValueText(text = stringResource(id = R.string.value_unknown))
        list.isEmpty() -> ValueText(text = stringResource(id = emptyId))
        else -> {
            list.forEach {
                ValueText(text = it)
            }
        }
    }
}

@Composable
private fun SingleValue(value: String?, labelId: Int, emptyId: Int = R.string.value_none) {
    LabelText(text = stringResource(id = labelId))
    ValueText(
        text = when {
            value == null -> stringResource(id = R.string.value_unknown)
            value.isEmpty() -> stringResource(id = emptyId)
            else -> value
        }
    )
}

@Composable
private fun SingleValue(value: Int?, labelId: Int) {
    LabelText(text = stringResource(id = labelId))
    ValueText(text = value?.toString() ?: stringResource(id = R.string.value_unknown))
}

@Composable
private fun LabelText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun ValueText(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(top = 10.dp),
    )
}

@Preview
@Composable
private fun DetailContentPreview() {
    val uiState = DetailUiState(
        entry = Entry(
            id = 62,
            name = "armored carp",
            category = "creatures",
            description = "Calcium deposits in the scales of this ancient fish make them as hard as armor." +
                    " Cooking it into a dish will fortify your bones, temporarily increasing your defense.",
            image = "https://botw-compendium.herokuapp.com/api/v3/compendium/entry/armored_carp/image",
            commonLocations = listOf("East Necluda", "Lanayru Great Spring"),
            dlc = false,
            drops = emptyList(),
            edible = true,
            heartsRecovered = 1.0f,
            cookingEffect = "defense up",
        )
    )

    HcTheme {
        DetailContent(uiState = uiState)
    }
}