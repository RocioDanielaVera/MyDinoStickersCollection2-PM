package com.example.mydinostickerscollection2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mydinostickerscollection2.data.User
import com.example.mydinostickerscollection2.databinding.ActivityPurchaseBinding
import com.example.mydinostickerscollection2.paymethods.Mastercard
import com.example.mydinostickerscollection2.paymethods.MedioDePago
import com.example.mydinostickerscollection2.paymethods.MercadoPago
import com.example.mydinostickerscollection2.paymethods.Visa
import com.example.mydinostickerscollection2.repositories.StickersRepository
import com.example.mydinostickerscollection2.repositories.UserRepository


class PurchaseActivity : AppCompatActivity() {
    private var sobresActual: Int = 1
    private lateinit var binding: ActivityPurchaseBinding
    private var totalAmount = 0.0
    private var comision = 0.0
    //private var medioDePago: MedioDePago? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nickname = intent.extras?.getString("USER_NICKNAME").orEmpty()
        val password = intent.extras?.getString("USER_PASSWORD").orEmpty()
        val usuario: User? = UserRepository.login(nickname, password)
        initListeners()
        initPaymentButtons()
        initPayNowButton(usuario)
        //Inicializo los valores de el sobre precargado en pantalla
        binding.tvUserItems.text = "$sobresActual"
        binding.tvUserSubtotal.text = "${getSubtotal()}"
    }
    private fun initPayNowButton(user: User?) {
        binding.btnBuyNow.setOnClickListener {
            if (totalAmount == 0.0) {
                Toast.makeText(this, "ERROR. Seleccione un medio de pago", Toast.LENGTH_LONG).show()
            } else if (user?.money!! < totalAmount) {
                val intentFail = Intent(this, FailActivity::class.java)
                intentFail.putExtra("USER_NICKNAME", user.nickName)
                intentFail.putExtra("USER_PASSWORD", user.password)
                startActivity(intentFail)
            } else {
                user.money -= totalAmount

                //creo los nuevos stickers que compro el usuario
                val nuevosStickers = StickersRepository.getRandomPackOfStickersByTotal(sobresActual)
                //agrego los nuevos stickers a la coleccion del usuario
                user.stickerCollection.addAll(nuevosStickers)

                val intentSuccess = Intent(this, SuccessActivity::class.java)
                intentSuccess.putExtra("USER_NICKNAME", user.nickName)
                intentSuccess.putExtra("USER_PASSWORD", user.password)
                startActivity(intentSuccess)
            }
        }
    }

    private fun getSubtotal(): Double {
        return sobresActual * 1000.0
    }

    fun initPaymentButtons() {
        var medioDePago: MedioDePago
        binding.btnVisa.setOnClickListener {
            medioDePago = Visa()
            totalAmount = medioDePago.calcularMontoFinal(getSubtotal())
            comision = (medioDePago as Visa).comision
            binding.tvUserComision.text = buildString { append("$comision") }
            binding.tvUserTotal.text = buildString { append("$totalAmount") }
        }

        binding.btnMastercard.setOnClickListener {
            medioDePago = Mastercard()
            totalAmount = medioDePago.calcularMontoFinal(getSubtotal())
            comision = (medioDePago as Mastercard).comision
            binding.tvUserComision.text = buildString { append("$comision") }
            binding.tvUserTotal.text = buildString { append("$totalAmount") }
        }

        binding.btnMercadopago.setOnClickListener {
            medioDePago = MercadoPago()
            totalAmount = medioDePago.calcularMontoFinal(getSubtotal())
            comision = getSubtotal() * (medioDePago as MercadoPago).comision
            binding.tvUserComision.text = buildString { append("$comision") }
            binding.tvUserTotal.text = buildString { append("$totalAmount") }
        }
    }

    fun initListeners() {
        binding.tvCountStickerpack.text = buildString { append("$sobresActual") }

        binding.btnMinus.setOnClickListener {
            if (sobresActual > 1) {
                sobresActual = sobresActual.minus(1)
                binding.tvCountStickerpack.text = buildString { append("$sobresActual") }
                binding.tvUserItems.text = buildString { append("$sobresActual") }
                binding.tvUserSubtotal.text = buildString { append("${getSubtotal()}") }
            }
        }
        binding.btnPlus.setOnClickListener {
            if (sobresActual < 10) {
                sobresActual = sobresActual.plus(1)
                binding.tvCountStickerpack.text = buildString { append("$sobresActual") }
                binding.tvUserItems.text = buildString { append("$sobresActual") }
                binding.tvUserSubtotal.text = buildString { append("${getSubtotal()}") }
                //Sumo lo sugerido por el profe
//                val currentTotalAmount = binding.tvUserTotal.text
//                if (currentTotalAmount.isNotEmpty()) {
//                    val newTotalAmount = currentTotalAmount.toString().toFloat().plus(getSubtotal())
//                    binding.tvUserTotal.text = buildString { append("$newTotalAmount") }
//                }
            }
        }

        //SEGUNDA OPCIÓN
//    private fun updateResume(){
//        val subtotal = getSubtotal()
//        binding.tvUserSubtotal.text = "$subtotal"
//
//        medioDePago?.let {
//            val totalAmount = it.calcularMontoFinal(subtotal)
//            val comision = totalAmount - subtotal
//            binding.tvUserComision.text = "$comision"
//            binding.tvUserTotal.text = "$totalAmount"
//        } ?: run {
//            binding.tvUserComision.text = "-"
//            binding.tvUserTotal.text = "-"
//        }
//    }
//        fun initPaymentButtons() {
//        var medioDePago: MedioDePago
//            binding.btnVisa.setOnClickListener {
//                medioDePago = Visa()
//            updateResume()
//            }
//
//            binding.btnMastercard.setOnClickListener {
//                medioDePago = Mastercard()
///             updateResume()
//            }
//
//            binding.btnMercadopago.setOnClickListener {
//                medioDePago = MercadoPago()
//            updateResume()
//            }
//        }
//
//        fun initListeners() {
//            binding.tvCountStickerpack.text = buildString { append("$sobresActual") }
//
//            binding.btnMinus.setOnClickListener {
//                if (sobresActual > 1) {
//                    sobresActual = sobresActual.minus(1)
//                    binding.tvCountStickerpack.text = buildString { append("$sobresActual") }
//                    binding.tvUserItems.text = buildString { append("$sobresActual") }
//                updateResume()
//                }
//            }
//            binding.btnPlus.setOnClickListener {
//                if (sobresActual < 10) {
//                    sobresActual = sobresActual.plus(1)
//                    binding.tvCountStickerpack.text = buildString { append("$sobresActual") }
//                    binding.tvUserItems.text = buildString { append("$sobresActual") }
//                updateResume()
//                }
//            }
//        }
    }
}