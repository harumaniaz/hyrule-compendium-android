package com.example.hyrulecompendium.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.hyrulecompendium.data.remote.Entry
import com.example.hyrulecompendium.ui.theme.HcTheme
import com.example.hyrulecompendium.ui.theme.IdTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryRow(
    entry: Entry,
    onClick: () -> Unit = {}
) {
    ListItem(
        headlineText = {
            Text(
                text = entry.name,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        supportingText = {
            Text(
                text = entry.category,
                fontSize = 16.sp
            )
        },
        leadingContent = {
            Box(modifier = Modifier.size(80.dp)) {
                AsyncImage(
                    model = entry.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        },
        trailingContent = {
            Text(
                text = entry.id.toString(),
                color = IdTextColor,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    )
}

@Preview
@Composable
fun EntryRowPreview() {
    HcTheme {
        EntryRow(
            entry = Entry(
                id = 147,
                name = "lynel",
                category = "monsters",
                description = "These fearsome monsters have lived in Hyrule since ancient times.",
                image = "https://botw-compendium.herokuapp.com/api/v3/compendium/entry/lynel/image",
                dlc = false
            )
        )
    }
}