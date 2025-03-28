package com.example.tlucontact.ui.myui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tlucontact.entities.CBGV
import com.example.tlucontact.entities.DVDB

@Composable
fun DetailScreen(navController: NavController, id: String, isCBGV: Boolean) {
    // Xác định đối tượng được click từ danh sách ( DVDB hoặc CBGV)
    val item = if (isCBGV) {
        cbgvList.find { it.id == id }
    } else {
        dvdbList.find { it.id == id }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Nút Back
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
        }

        Spacer(modifier = Modifier.height(16.dp))
        // từ item xác định compose nào sẽ được hiển thị trong màn hình detail
        item?.let {
            when (it) {
                is CBGV -> CBGVDetail(it)
                is DVDB -> DVDBDetail(it)
            }
        }
    }
}
// ✅ Màn hiển thị chi tiết cho CBGV
@Composable
fun CBGVDetail(cbgv: CBGV) {
    // Dùng Card để tạo khối thông tin với viền bo tròn và nền màu xanh nhạt
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)) // Xanh nhạt
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Tên giảng viên
            Text(text = cbgv.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            // Các dòng thông tin chi tiết
            DetailRow(label = "Chức vụ", value = cbgv.position)
            DetailRow(label = "Khoa/Ban", value = cbgv.wordDepartment)
            DetailRow(label = "Số điện thoại", value = cbgv.phoneNumber)
            DetailRow(label = "Email", value = cbgv.email)

            Spacer(modifier = Modifier.height(16.dp))

            // Các nút hành động (gọi, email)
            ActionButtons(phone = cbgv.phoneNumber, email = cbgv.email)
        }
    }
}

// ✅ Màn hiển thị chi tiết cho đơn vị DVDB
@Composable
fun DVDBDetail(dvdb: DVDB) {
    // Dùng Card với nền cam nhạt để phân biệt
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)) // Cam nhạt
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Tên đơn vị
            Text(text = dvdb.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            // Các dòng thông tin chi tiết
            DetailRow(label = "Địa chỉ", value = dvdb.address)
            DetailRow(label = "Số điện thoại", value = dvdb.phoneNumber)
            DetailRow(label = "Email", value = dvdb.email)

            Spacer(modifier = Modifier.height(16.dp))

            // Các nút hành động (gọi, email, xem địa chỉ trên bản đồ)
            ActionButtons(phone = dvdb.phoneNumber, email = dvdb.email, address = dvdb.address)
        }
    }
}

// ✅ Hàm tạo dòng hiển thị nhãn + giá trị
@Composable
fun DetailRow(label: String, value: String) {
    Text(text = "$label: $value", fontSize = 16.sp)
    Spacer(modifier = Modifier.height(4.dp))
}

// ✅ Các nút hành động tương tác (gọi điện, gửi email, mở bản đồ)
@Composable
fun ActionButtons(phone: String, email: String, address: String? = null) {
    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly // căn đều các icon
    ) {
        // Nút gọi điện
        IconButton(onClick = {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
            context.startActivity(intent)
        }) {
            Icon(Icons.Default.Call, contentDescription = "Gọi điện", tint = Color.Green)
        }

        // Nút gửi email
        IconButton(onClick = {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
            context.startActivity(intent)
        }) {
            Icon(Icons.Default.Email, contentDescription = "Gửi Email", tint = Color.Blue)
        }

        // Nếu có địa chỉ, thêm nút xem bản đồ
        address?.let {
            IconButton(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=$address"))
                context.startActivity(intent)
            }) {
                Icon(Icons.Default.LocationOn, contentDescription = "Xem trên bản đồ", tint = Color.Red)
            }
        }
    }
}
