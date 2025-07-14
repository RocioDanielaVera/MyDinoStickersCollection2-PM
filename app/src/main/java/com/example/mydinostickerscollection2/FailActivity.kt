package com.example.mydinostickerscollection2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mydinostickerscollection2.databinding.ActivityFailBinding

class FailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnVolverInicio.setOnClickListener {
            val nickname = intent.getStringExtra("USER_NICKNAME").orEmpty()
            val password = intent.getStringExtra("USER_PASSWORD").orEmpty()

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USER_NICKNAME", nickname)
            intent.putExtra("USER_PASSWORD", password)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}