package com.example.mydinostickerscollection2
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mydinostickerscollection2.data.User
import com.example.mydinostickerscollection2.databinding.ActivityCollectionBinding
import com.example.mydinostickerscollection2.repositories.UserRepository

class CollectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCollectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nickname = intent.extras?.getString("USER_NICKNAME").orEmpty()
        val password = intent.extras?.getString("USER_PASSWORD").orEmpty()
        val usuario: User? = UserRepository.login(nickname, password)
    }
}