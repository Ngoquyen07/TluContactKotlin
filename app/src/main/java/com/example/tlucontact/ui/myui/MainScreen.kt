
package com.example.tlucontact.ui.myui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose  .rememberNavController
import com.example.tlucontact.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var searchText by remember { mutableStateOf(TextFieldValue("")) } // text tìm kiếm
    var selectedScreen by remember { mutableStateOf(Screen.DVDBScreen) } // màn hình hiển thị . Mặc định DVDB
    var sortType by remember { mutableStateOf(SortType.BY_POSITION) } // Mặc định sắp xếp theo chức vụ
    Scaffold(
        // topbar bao gồm thanh tìm kiếm và nút sắp xếp
        topBar = {
            SearchTopBar(
                searchText,
                onSearchTextChange = { searchText = it },
                onSortClick = {
                    sortType = if (sortType == SortType.BY_POSITION) SortType.BY_DEPARTMENT else SortType.BY_POSITION
                }
            )
        },
        // bottomBar bao gồm các butotn đề điều hướng màn hình hiển thị.
        bottomBar = {
            BottomNavigationBar(selectedScreen) { selectedScreen = it }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController = navController, startDestination = "main") {
                composable("main") { // navHost main bao gồm 2 màn hình : DVDB và CBGV
                    when (selectedScreen) {
                        Screen.CBGVScreen -> CBGVScreen(navController,  // nếu màn hình được chọn là CBGV thì CBGVScreen được hiển thị
                            searchText.text, sortType)
                        Screen.DVDBScreen -> DVDBScreen(navController,  // nếu màn hình được chọn là DVDB thì DVDBScreen được hiển thị
                            searchText.text, sortType)
                    }
                }
                composable("detail/{id}/{isCBGV}") { backStackEntry -> // NavHost detail hiển thị chi tiết thông tin
                                                                            // của CBGV hoặc DVDB được chọn
                    val id = backStackEntry.arguments?.getString("id") ?: "" // lấy ra id của CBGV hoặc DVDB được chọn
                    val isCBGV = backStackEntry.arguments?.getString("isCBGV")?.toBoolean() ?: true // lấy ra kiểu của CBGV hoặc DVDB được chọn
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
            // ô tìm kiếm
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
            // nút sắp xếp
            IconButton(onClick = onSortClick) {
                Icon(
                    painter = painterResource(R.drawable.sorticon),
                    contentDescription = "Sắp xếp",
                    tint = Color.White
                )
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
    // sử dụng NavigationBar để tạo thanh điều hướng
    NavigationBar {
        // mỗi item tương ứng với 1 màn hình
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Đơn vị danh bạ") },
            label = { Text("DVDB") },
            selected = selectedScreen == Screen.DVDBScreen,
            onClick = { onScreenSelected(Screen.DVDBScreen) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Cán bộ giảng viên") },
            label = { Text("CBGV") },
            selected = selectedScreen == Screen.CBGVScreen,
            onClick = { onScreenSelected(Screen.CBGVScreen) }
        )
    }
}

// ✅ Enum màn hình
enum class Screen { DVDBScreen, CBGVScreen } // lớp enum bao gồm 2 màn hình : DVDB và CBGV

// ✅ Enum kiểu sắp xếp
enum class SortType { BY_POSITION, BY_DEPARTMENT } // lớp enum bao gồm 2 kiểu sắp xếp : theo chức vụ và theo khoa/ban

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}