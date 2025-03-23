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

        item?.let {
            when (it) {
                is CBGV -> CBGVDetail(it)
                is DVDB -> DVDBDetail(it)
            }
        }
    }
}

@Composable
fun CBGVDetail(cbgv: CBGV) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)) // Xanh nhạt
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = cbgv.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            DetailRow(label = "Chức vụ", value = cbgv.position)
            DetailRow(label = "Khoa/Ban", value = cbgv.wordDepartment)
            DetailRow(label = "Số điện thoại", value = cbgv.phoneNumber)
            DetailRow(label = "Email", value = cbgv.email)
            Spacer(modifier = Modifier.height(16.dp))
            ActionButtons(phone = cbgv.phoneNumber, email = cbgv.email)
        }
    }
}

@Composable
fun DVDBDetail(dvdb: DVDB) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)) // Cam nhạt
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = dvdb.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            DetailRow(label = "Địa chỉ", value = dvdb.address)
            DetailRow(label = "Số điện thoại", value = dvdb.phoneNumber)
            DetailRow(label = "Email", value = dvdb.email)
            Spacer(modifier = Modifier.height(16.dp))
            ActionButtons(phone = dvdb.phoneNumber, email = dvdb.email, address = dvdb.address)
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Text(text = "$label: $value", fontSize = 16.sp)
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun ActionButtons(phone: String, email: String, address: String? = null) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(onClick = {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
            context.startActivity(intent)
        }) {
            Icon(Icons.Default.Call, contentDescription = "Gọi điện", tint = Color.Green)
        }

        IconButton(onClick = {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
            context.startActivity(intent)
        }) {
            Icon(Icons.Default.Email, contentDescription = "Gửi Email", tint = Color.Blue)
        }

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
