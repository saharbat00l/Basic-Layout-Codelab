/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.basiclayoutcodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basiclayoutcodelab.ui.theme.BasicLayoutCodelabTheme
import java.util.*

//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
//import androidx.compose.foundation.lazy.grid.items


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicLayoutCodelab()
        }
    }

    // Step: Search bar - Modifiers
    @Composable
    fun SearchBar(
        modifier: Modifier = Modifier
    ) {
        TextField(
            value = "",
            onValueChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
            placeholder = {
                Text(stringResource(R.string.placeholder_search))
            },
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
        )
    }

    // Step: Align your body - Alignment
    @Composable
    fun FirstCard(
        @DrawableRes drawable: Int, @StringRes text: Int, modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(drawable),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.paddingFromBaseline(
                    top = 18.dp, bottom = 40.dp
                )
            )
        }
    }

    // Step: Favorite collection card - Material Surface
    @Composable
    fun FavoriteCollectionCard(
        @DrawableRes drawable: Int, @StringRes text: Int, modifier: Modifier = Modifier
    ) {
        Surface(
            shape = MaterialTheme.shapes.small,
            modifier = modifier
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.width(192.dp)
            ) {
                Image(
                    painter = painterResource(drawable),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(56.dp)
                )
                Text(
                    text = stringResource(text),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }

    // Step: Align your body row - Arrangements
    @Composable
    fun FirstScroll(
        modifier: Modifier = Modifier
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = modifier
        ) {
            items(firstScrollData) { item ->
                FirstCard(drawable = item.drawable, text = item.text)
            }
        }
    }

    // Step: Favorite collections grid - LazyGrid
    @Composable
    fun FavoriteCollectionsGrid(
        modifier: Modifier = Modifier
    ) {
        LazyRow(
//        rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.height(90.dp)
        ) {
            items(favoriteCollectionsData) { item ->
                FavoriteCollectionCard(
                    drawable = item.drawable,
                    text = item.text,
                    modifier = Modifier.height(80.dp)
                )
            }
        }
    }

    // Step: Home section - Slot APIs
    @Composable
    fun HomeSection(
        @StringRes title: Int,
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) {
        Column(modifier) {
            Text(
                text = stringResource(title).uppercase(Locale.getDefault()),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .paddingFromBaseline(top = 40.dp, bottom = 30.dp)
                    .padding(horizontal = 16.dp)
            )
            content()
        }
    }

    // Step: Home screen - Scrolling
    @Composable
    fun HomeScreen(modifier: Modifier = Modifier) {
        Column(
            modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 20.dp)
        ) {
            SearchBar(Modifier.padding(horizontal = 16.dp))
            HomeSection(title = R.string.first_head) {
                FirstScroll()
            }
            Spacer(modifier = Modifier.height(30.dp))
            HomeSection(title = R.string.second_head) {
                FavoriteCollectionsGrid()
            }
        }
    }

    // Step: Bottom navigation - Material
    @Composable
    private fun SootheBottomNavigation(modifier: Modifier = Modifier) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.background, modifier = modifier
        ) {
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null
                    )
                },
                label = {
                    Text(stringResource(R.string.bottom_navigation_home))
                },
                selected = true,
                onClick = {}
            )
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null
                    )
                },
                label = {
                    Text(stringResource(R.string.bottom_navigation_profile))
                },
                selected = false,
                onClick = {}
            )
        }
    }

    // Step: MySoothe App - Scaffold
    @Composable
    fun BasicLayoutCodelab() {
        BasicLayoutCodelabTheme() {
            Scaffold(
                bottomBar = { SootheBottomNavigation() }
            ) { padding ->
                HomeScreen(Modifier.padding(padding))
            }
        }
    }

    private val firstScrollData = listOf(
        R.drawable.user to R.string.user,
        R.drawable.user to R.string.user,
        R.drawable.user to R.string.user,
        R.drawable.user to R.string.user,
        R.drawable.user to R.string.user,
        R.drawable.user to R.string.user
    ).map { DrawableStringPair(it.first, it.second) }

    private val favoriteCollectionsData = listOf(
        R.drawable.fav to R.string.fav,
        R.drawable.fav to R.string.fav,
        R.drawable.fav to R.string.fav,
        R.drawable.fav to R.string.fav,
        R.drawable.fav to R.string.fav,
        R.drawable.fav to R.string.fav
    ).map { DrawableStringPair(it.first, it.second) }

    private data class DrawableStringPair(
        @DrawableRes val drawable: Int,
        @StringRes val text: Int
    )

    @Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
    @Composable
    fun SearchBarPreview() {
        BasicLayoutCodelabTheme() { SearchBar(Modifier.padding(8.dp)) }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
    @Composable
    fun FirstCardElementPreview() {
        BasicLayoutCodelabTheme() {
            FirstCard(
                text = R.string.user,
                drawable = R.drawable.user,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
    @Composable
    fun FavoriteCollectionCardPreview() {
        BasicLayoutCodelabTheme() {
            FavoriteCollectionCard(
                text = R.string.fav,
                drawable = R.drawable.fav,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
    @Composable
    fun FavoriteCollectionsGridPreview() {
        BasicLayoutCodelabTheme() { FavoriteCollectionsGrid() }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
    @Composable
    fun FirstScrollPreview() {
        BasicLayoutCodelabTheme() { FirstScroll() }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
    @Composable
    fun HomeSectionPreview() {
        BasicLayoutCodelabTheme() {
            HomeSection(R.string.first_head) {
                FirstScroll()
            }
        }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
    @Composable
    fun ScreenContentPreview() {
        BasicLayoutCodelabTheme() { HomeScreen() }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
    @Composable
    fun BottomNavigationPreview() {
        BasicLayoutCodelabTheme() { SootheBottomNavigation(Modifier.padding(top = 24.dp)) }
    }

    @Preview(widthDp = 360, heightDp = 640)
    @Composable
    fun MySoothePreview() {
        BasicLayoutCodelab()
    }
}
