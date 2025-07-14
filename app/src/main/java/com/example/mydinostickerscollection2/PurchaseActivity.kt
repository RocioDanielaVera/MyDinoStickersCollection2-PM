package com.example.mydinostickerscollection2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mydinostickerscollection2.databinding.ActivityPurchaseBinding
import com.example.mydinostickerscollection2.paymethods.Mastercard
import com.example.mydinostickerscollection2.paymethods.MedioDePago
import com.example.mydinostickerscollection2.paymethods.MercadoPago
import com.example.mydinostickerscollection2.paymethods.Visa


class PurchaseActivity : AppCompatActivity() {
    private var sobresActual: Int = 1
    private lateinit var binding: ActivityPurchaseBinding
    private var totalAmount = 0.0
    private var comision = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val nickname = intent.extras?.getString("USER_NICKNAME").orEmpty()
//        val password = intent.extras?.getString("USER_PASSWORD").orEmpty()
//        val usuario:User? = UserRepository.login(nickname, password)
        initListeners()
        initPaymentButtons()
//        initPayNowButton(usuario)

    }

//    private fun initPayNowButton(user: User?) {
//
//        binding.btnBuyNow.setOnClickListener {
//            if (totalAmount == 0.0){
//                Toast.makeText(this, "ERROR. Seleccione un medio de pago", Toast.LENGTH_LONG).show()
//            }else if(user?.money!! < totalAmount){
//                val intentFail = Intent(this, FailActivity::class.java)
//                startActivity(intentFail)
//            }else{
//                val intentSuccess = Intent(this, SuccessActivity::class.java)
//                startActivity(intentSuccess)
//            }
//        }
//
//    }

    private fun getSubtotal():Double{
        return sobresActual * 1000.0
    }


    fun initPaymentButtons(){
        var medioDePago: MedioDePago
        binding.btnVisa.setOnClickListener {
            medioDePago = Visa()
            totalAmount = medioDePago.calcularMontoFinal(getSubtotal())
            comision = (medioDePago as Visa).comision
            binding.tvUserComision.text= buildString { append("$comision") }
            binding.tvUserTotal.text = buildString { append("$totalAmount") }
        }

        binding.btnMastercard.setOnClickListener {
            medioDePago = Mastercard()
            totalAmount = medioDePago.calcularMontoFinal(getSubtotal())
            binding.tvUserComision.text= buildString { append("$comision") }
            binding.tvUserTotal.text = buildString { append("$totalAmount") }
        }

        binding.btnMercadopago.setOnClickListener {
            medioDePago = MercadoPago()
            totalAmount = medioDePago.calcularMontoFinal(getSubtotal())
            binding.tvUserComision.text= buildString { append("$comision") }
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
                binding.tvUserSubtotal.text = buildString { append("${getSubtotal()}")}
            }
        }
        binding.btnPlus.setOnClickListener {
            if (sobresActual < 10) {
                sobresActual = sobresActual.plus(1)
                binding.tvCountStickerpack.text = buildString { append("$sobresActual") }
                binding.tvUserItems.text = buildString { append("$sobresActual") }
                binding.tvUserSubtotal.text = buildString { append("${getSubtotal()}")}

            }
        }


    }


}