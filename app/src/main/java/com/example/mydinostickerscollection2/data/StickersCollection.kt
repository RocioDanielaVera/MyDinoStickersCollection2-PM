package com.example.mydinostickerscollection2.data

data class StickersCollection(val id: Long,
                              val userId: Long,
                              val stickers: MutableList<Long>)
