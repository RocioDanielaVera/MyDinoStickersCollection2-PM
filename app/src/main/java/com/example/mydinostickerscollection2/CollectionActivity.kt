package com.example.mydinostickerscollection2
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mydinostickerscollection2.adapter.StickersAdapter
import com.example.mydinostickerscollection2.data.Sticker
import com.example.mydinostickerscollection2.databinding.ActivityCollectionBinding
import com.example.mydinostickerscollection2.repositories.StickersRepository


class CollectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCollectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        val selectStickerClickLister = {
                sticker: Sticker ->
            Toast.makeText(this, "Seleccionaste al sticker", Toast.LENGTH_LONG).show()
        }
        //Este es un ejemplo.No esta desarrollado
        binding.textView2.text = buildString {  append("Coleccionadas: ${StickersRepository.getStickers().size} \n Repetidas: 6 \n ¡Te faltan 6 figuritas más para completar tu colección!") }
        //Este inicializa el adapter
        binding.recyclerViewStickers.adapter = StickersAdapter(StickersRepository.getStickers(),selectStickerClickLister )
       //Este muestra los items en 2
        binding.recyclerViewStickers.layoutManager = GridLayoutManager(this, 2)
    }

}