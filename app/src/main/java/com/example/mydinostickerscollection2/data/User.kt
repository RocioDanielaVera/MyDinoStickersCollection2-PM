package com.example.mydinostickerscollection2.data

data class User(val id: Long,
                val nickName: String,
                val password: String,
                val name: String,
                val surname: String,
                var money: Double,
                val createdDate: String,
                val stickerCollection: MutableList<Long> = mutableListOf()
)
