package com.example.hyrulecompendium.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hyrulecompendium.R
import com.example.hyrulecompendium.data.GameType
import com.example.hyrulecompendium.ui.theme.HcTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HcTopBar(
    title: String,
    navigationIcon: ImageVector? = null,
    onNavigationClick: () -> Unit = {},
    actionIcon: ImageVector? = null,
    onActionClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.texture),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

        }
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navigationIcon?.let {
                IconButton(
                    onClick = onNavigationClick,
                    Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = null,
                    )
                }
            }

            Text(
                text = title,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
                    .basicMarquee(),
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge
            )

            actionIcon?.let {
                IconButton(
                    onClick = onActionClick,
                    Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = null,
                    )
                }
            }
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            thickness = 1.dp
        )
    }
}

@Preview
@Composable
private fun HcTopBarPreview() {
    GameType.initialize(LocalContext.current)
    HcTheme {
        HcScaffold(
            topBar = {
                HcTopBar(
                    title = GameType.BOTW.title,
                    navigationIcon = Icons.Outlined.ArrowBack,
                    actionIcon = Icons.Outlined.Settings
                )
            }
        )
    }
}