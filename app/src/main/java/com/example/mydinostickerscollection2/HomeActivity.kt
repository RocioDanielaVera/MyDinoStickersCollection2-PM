package com.example.mydinostickerscollection2
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mydinostickerscollection2.data.User
import com.example.mydinostickerscollection2.databinding.ActivityHomeBinding
import com.example.mydinostickerscollection2.repositories.UserRepository

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nickname = intent.extras?.getString("USER_NICKNAME").orEmpty()
        val password = intent.extras?.getString("USER_PASSWORD").orEmpty()
        val usuario:User? = UserRepository.login(nickname, password)
        initUserInformation(usuario)
        purchaseSetUp()
        collectionSetUp()
    }
    fun initUserInformation(usuario: User?){
        binding.tvUsername.text = buildString { append("Hola, ${usuario?.nickName}" ) }
        binding.tvUserid.text = buildString { append(usuario?.id) }
        binding.tvUserdate.text = buildString { append(usuario?.createdDate) }
        binding.tvFullname.text = buildString { append("${usuario?.name} ${usuario?.surname}") }
    }

    fun purchaseSetUp(){
        binding.btnPurchase.setOnClickListener {
            val nickname = intent.extras?.getString("USER_NICKNAME").orEmpty()
            val password = intent.extras?.getString("USER_PASSWORD").orEmpty()

            val intentPurchase = Intent(this, PurchaseActivity::class.java)
            intentPurchase.putExtra("USER_NICKNAME", nickname)
            intentPurchase.putExtra("USER_PASSWORD", password)
            startActivity(intentPurchase)
        }
    }

    fun collectionSetUp(){
        binding.buttonCollection.setOnClickListener {
            val intentCollection= Intent(this, CollectionActivity::class.java)
            startActivity(intentCollection)
        }
    }

}