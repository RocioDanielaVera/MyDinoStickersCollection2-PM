package com.example.mydinostickerscollection2
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mydinostickerscollection2.databinding.ActivityMainBinding
import com.example.mydinostickerscollection2.repositories.UserRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeSetUp()
    }
    fun homeSetUp(){
        binding.btnIngresar.setOnClickListener {
            val inputUser = binding.etUserNickname.text.toString()
            val inputPassword = binding.etPassword.text.toString()

            if(UserRepository.verifyUser(inputUser, inputPassword)){
                initUserAccount(inputUser, inputPassword)
            }else{
                Toast.makeText(this, "Usuario no registrado, intente otra vez", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun initUserAccount(nickname: String, password:String){
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("USER_NICKNAME", nickname)
        intent.putExtra("USER_PASSWORD", password)
        startActivity(intent)
    }

    override fun onDestroy(){
        super.onDestroy()

    }

}