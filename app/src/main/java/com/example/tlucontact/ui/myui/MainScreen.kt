
package com.example.tlucontact.ui.myui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    var selectedScreen by remember { mutableStateOf(Screen.DVDBScreen) }
    var sortType by remember { mutableStateOf(SortType.BY_POSITION) } // Mặc định sắp xếp theo chức vụ

    Scaffold(
        topBar = {
            SearchTopBar(
                searchText,
                onSearchTextChange = { searchText = it },
                onSortClick = {
                    sortType = if (sortType == SortType.BY_POSITION) SortType.BY_DEPARTMENT else SortType.BY_POSITION
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(selectedScreen) { selectedScreen = it }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController = navController, startDestination = "main") {
                composable("main") {
                    when (selectedScreen) {
                        Screen.CBGVScreen -> CBGVScreen(navController,
                            searchText.text, sortType)
                        Screen.DVDBScreen -> DVDBScreen(navController,
                            searchText.text, sortType)
                    }
                }
                composable("detail/{id}/{isCBGV}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id") ?: ""
                    val isCBGV = backStackEntry.arguments?.getString("isCBGV")?.toBoolean() ?: true
                    DetailScreen(navController, id, isCBGV)
                }
            }
        }
    }
}

// ✅ TopBar có ô tìm kiếm + Nút sắp xếp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    searchText: TextFieldValue,
    onSearchTextChange: (TextFieldValue) -> Unit,
    onSortClick: () -> Unit
) {
    TopAppBar(
        title = {
            TextField(
                value = searchText,
                onValueChange = { onSearchTextChange(it) },
                placeholder = { Text("Tìm kiếm...") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        },
        actions = {
            IconButton(onClick = onSortClick) {
                Icon(Icons.Default.List, contentDescription = "Sắp xếp")
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xF866B64D), titleContentColor = Color.White
        )
    )
}

// ✅ Bottom Navigation
@Composable
fun BottomNavigationBar(selectedScreen: Screen, onScreenSelected: (Screen) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Trang chủ") },
            label = { Text("DVDB") },
            selected = selectedScreen == Screen.DVDBScreen,
            onClick = { onScreenSelected(Screen.DVDBScreen) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Hồ sơ") },
            label = { Text("CBGV") },
            selected = selectedScreen == Screen.CBGVScreen,
            onClick = { onScreenSelected(Screen.CBGVScreen) }
        )
    }
}

// ✅ Enum màn hình
enum class Screen { DVDBScreen, CBGVScreen }

// ✅ Enum kiểu sắp xếp
enum class SortType { BY_POSITION, BY_DEPARTMENT }

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}