package com.example.mydinostickerscollection2.repositories

import com.example.mydinostickerscollection2.data.Sticker
import com.example.mydinostickerscollection2.data.StickersCollection
import com.example.mydinostickerscollection2.data.User

object StickersCollectionRepository {
    private val stickersCollections = mutableListOf<StickersCollection>()

    init {
        stickersCollections.add(
            StickersCollection(
                1L,
                1510L,
                mutableListOf(1L, 3L, 12L, 27L, 5L, 19L, 8L, 30L, 2L, 14L, 22L, 9L)
            )
        )
        stickersCollections.add(
            StickersCollection(
                2L,
                1504L,
                mutableListOf(1L, 3L, 6L, 17L, 30L, 11L, 24L, 3L, 29L, 18L, 6L, 10L)
            )
        )
        stickersCollections.add(
            StickersCollection(
                3L,
                2802L,
                mutableListOf(1L, 3L, 25L, 7L, 14L, 30L, 2L, 12L, 28L, 19L, 5L, 25L)
            )
        )
    }

    fun get(userId:Long): List<StickersCollection> {
        val lista = stickersCollections.filter { it.userId == userId }
        return lista
    }

    fun getListOfStickersFromUser(userId:Long): List<Sticker> {
        var stickersId = get(userId).flatMap { it.stickers }
        var listOfStickerId = StickersRepository.getStickers().map { it.id }.toList()
        var listAsStickers = mutableListOf<Sticker>()
        for (id in stickersId){
            if(listOfStickerId.contains(id)){
                listAsStickers.add(StickersRepository.getStickerById(id)!!)
            }
        }
        return listAsStickers.toList()
    }

    fun addStickersToCollectionByUserId(stickers: List<Long>, userId: Long) {
        get(userId).map { it.stickers.addAll(stickers) }
    }

    fun obtenerFiguritasFaltantes(allStickerIds: List<Long>, userSticker: List<Long>): List<Long> {
        val userSet = userSticker.toSet()
        return allStickerIds.filter { it !in userSet }
    }

    fun showStickersCollection(user: User?): String {
        val listaUsuario = get(user?.id !!)

        val repetidas = listaUsuario.flatMap{it.stickers}.groupingBy { it }.eachCount()
        val filtradas = repetidas.filter { it.value > 1 }

        val filtrarSticker: List<Long> = StickersRepository.getStickers().map { it.id }
        val filtrarListaUsuario = listaUsuario.flatMap { it.stickers }

        val losQueFaltan = obtenerFiguritasFaltantes(filtrarSticker, filtrarListaUsuario)
        return """
            -LISTA DE USUARIO = 
            $listaUsuario
            -STICKERS QUE FALTAN = 
            $losQueFaltan
            -STICKERS REPETIDOS =
            $repetidas
            -STICKERS FILTRADOS =
            $filtradas
        """.trimIndent()
    }
}