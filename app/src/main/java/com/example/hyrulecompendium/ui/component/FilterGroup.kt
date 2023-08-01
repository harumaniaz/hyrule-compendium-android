package com.example.hyrulecompendium.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hyrulecompendium.data.CategoryType
import com.example.hyrulecompendium.ui.theme.HcTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFilterGroup(
    categories: List<CategoryType>,
    selectedItem: CategoryType,
    onClickItem: (CategoryType) -> Unit = {}
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(categories) { index, item ->
            if (index == 0) {
                Spacer(modifier = Modifier.width(10.dp))
            }

            FilterChip(
                selected = (item == selectedItem),
                onClick = { onClickItem(item) },
                label = { Text(text = item.label) },
            )

            if (index == categories.lastIndex) {
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@Preview
@Composable
fun CategoryFilterGroupPreview() {
    CategoryType.initialize(LocalContext.current)
    val categories = listOf(*CategoryType.values())

    HcTheme {
        HcScaffold {
            CategoryFilterGroup(
                categories = categories,
                selectedItem = categories[0]
            )
        }
    }
}