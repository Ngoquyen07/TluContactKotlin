package com.example.tlucontact.entities

data class CBGV(
    val id: String,
    val name: String,  // Đảm bảo 'name' là một thuộc tính
    val position: String,
    val phoneNumber: String,
    val email: String,
    val wordDepartment: String
)