package com.example.mydinostickerscollection2
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mydinostickerscollection2.adapter.StickersAdapter
import com.example.mydinostickerscollection2.data.Sticker
import com.example.mydinostickerscollection2.data.User
import com.example.mydinostickerscollection2.databinding.ActivityCollectionBinding
import com.example.mydinostickerscollection2.repositories.StickersRepository
import com.example.mydinostickerscollection2.repositories.UserRepository


class CollectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCollectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //obtengo los datos del usuario
        val nickname = intent.getStringExtra("USER_NICKNAME").orEmpty()
        val password = intent.getStringExtra("USER_PASSWORD").orEmpty()
        //
        val usuario: User? = UserRepository.login(nickname, password)
        //verifica que el usuario no es null
        if(usuario != null){
            setUpRecyclerView(usuario)
        } else {
            Toast.makeText(this, "Error al cargar la colección del usuario.", Toast.LENGTH_LONG).show()
        }
    }

    private fun setUpRecyclerView(usuario: User) {
        //buscamos los stickers del usuario por id
        val stickersDelUsuario = mutableListOf<Sticker>()
        for(id in usuario.stickerCollection){
            val sticker = StickersRepository.getStickerById(id)
            if(sticker != null){
                stickersDelUsuario.add(sticker)
            }
        }

        //calcular coleccionadas, repetidas y las que faltan
        val coleccionadas = stickersDelUsuario.distinctBy { it.id }.size
        val repetidas = stickersDelUsuario.size - coleccionadas
        val faltan = StickersRepository.getStickers().size - coleccionadas

        //Este es un ejemplo.No esta desarrollado
        binding.textView2.text = buildString {
            append("Coleccionadas: $coleccionadas\n")
            append("Repetidas: $repetidas\n")
            append("¡Te faltan $faltan figuritas más para completar tu colección!")
        }

        val selectStickerClickLister = {
                sticker: Sticker ->
            Toast.makeText(this, "Seleccionaste al sticker ${sticker.name}", Toast.LENGTH_LONG).show()
        }

        //Este inicializa el adapter y muestra los stickers del usuario
        binding.recyclerViewStickers.adapter = StickersAdapter(stickersDelUsuario,selectStickerClickLister)

        //Este muestra los items en 2
        binding.recyclerViewStickers.layoutManager = GridLayoutManager(this, 2)
    }
}