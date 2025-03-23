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
import com.example.tlucontact.entities.CBGV

// ✅ Danh sách mẫu CBGV

val cbgvList = listOf(
    CBGV("CBGV001", "TS. Nguyễn Văn A", "Giảng viên", "0123456789", "nguyenvana@tlu.edu.vn", "Khoa CNTT"),
    CBGV("CBGV002", "ThS. Trần Thị B", "Trưởng khoa", "0987654321", "tranthib@tlu.edu.vn", "Khoa Kinh tế"),
    CBGV("CBGV003", "PGS. TS. Lê Văn C", "Hiệu phó", "0369852147", "levanc@tlu.edu.vn", "Ban Giám Hiệu"),
    CBGV("CBGV004", "GS. TS. Phạm Văn D", "Hiệu trưởng", "0912345678", "phamvand@tlu.edu.vn", "Ban Giám Hiệu"),
    CBGV("CBGV005", "TS. Đặng Thị E", "Giảng viên", "0978123456", "dangthie@tlu.edu.vn", "Khoa Môi trường"),
    CBGV("CBGV006", "ThS. Bùi Văn F", "Trưởng bộ môn", "0967123456", "buivanf@tlu.edu.vn", "Khoa CNTT"),
    CBGV("CBGV007", "PGS. TS. Hoàng Văn G", "Phó hiệu trưởng", "0943123456", "hoangvang@tlu.edu.vn", "Ban Giám Hiệu"),
    CBGV("CBGV008", "ThS. Nguyễn Thị H", "Giảng viên", "0911223344", "nguyenthih@tlu.edu.vn", "Khoa Kinh tế")
)
@Composable
fun CBGVScreen(navController: NavController, searchQuery: String, sortType: SortType) {
    val filteredList = cbgvList.filter { it.name.contains(searchQuery, ignoreCase = true) }
        .sortedWith(
            when (sortType) {
                SortType.BY_POSITION -> compareBy { it.position }
                SortType.BY_DEPARTMENT -> compareBy { it.wordDepartment }
            }
        )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (filteredList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Không có kết quả", fontSize = 18.sp)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredList) { cbgv ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        onClick = {
                            navController.navigate("detail/${cbgv.id}/true")
                        }
                    ) {
                        Text(
                            text = cbgv.name,
                            modifier = Modifier.padding(16.dp),
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

