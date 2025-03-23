package com.example.tlucontact.ui.myui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tlucontact.entities.DVDB

val dvdbList = listOf(
    DVDB("DVDB001", "Phòng Đào Tạo", "02438581447", "daotao@tlu.edu.vn", "Số 1, Đại Cồ Việt, Hà Nội"),
    DVDB("DVDB002", "Phòng Hành Chính", "02438654321", "hanhchinh@tlu.edu.vn", "Số 1, Đại Cồ Việt, Hà Nội"),
    DVDB("DVDB003", "Phòng Kế Hoạch", "02438765432", "kehoach@tlu.edu.vn", "Số 1, Đại Cồ Việt, Hà Nội"),
    DVDB("DVDB004", "Trung tâm CNTT", "02438876543", "cntt@tlu.edu.vn", "Số 1, Đại Cồ Việt, Hà Nội"),
    DVDB("DVDB005", "Phòng Công Tác Sinh Viên", "02438987654", "ctsv@tlu.edu.vn", "Số 1, Đại Cồ Việt, Hà Nội"),
    DVDB("DVDB006", "Thư Viện", "02438123456", "thuvien@tlu.edu.vn", "Số 1, Đại Cồ Việt, Hà Nội")
)

@Composable
fun DVDBScreen(navController: NavController, searchQuery: String, sortType: SortType) {
    val filteredList = dvdbList.filter { it.name.contains(searchQuery, ignoreCase = true) }
        .sortedWith(
            when (sortType) {
                SortType.BY_POSITION -> compareBy { it.id }
                SortType.BY_DEPARTMENT -> compareBy { it.name }
            }
        )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (filteredList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Không có kết quả", fontSize = 18.sp)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredList) { dvdb ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        onClick = {
                            navController.navigate("detail/${dvdb.id}/false")
                        }
                    ) {
                        Text(
                            text = dvdb.name,
                            modifier = Modifier.padding(16.dp),
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

