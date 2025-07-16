package com.example.mydinostickerscollection2.paymethods

import android.util.Log
import java.time.LocalTime
import java.time.ZoneId

class Visa : MedioDePago {
    var comision:Double = 0.0
    var montoTotal:Double = 0.0
    val zonaArgentina: ZoneId = ZoneId.of("America/Argentina/Buenos_Aires")

    val horaArg: LocalTime = LocalTime.now(zonaArgentina)

    override fun calcularMontoFinal(montoBase: Double): Double {
        Log.d("Visa", "Hora actual ARG: $horaArg")
        if (horaArg in LocalTime.of(15, 0)..LocalTime.of(22, 30)) {
            this.comision = montoBase * 0.1
        } else {
            this.comision = montoBase * 0.3
        }
        this.montoTotal = montoBase + this.comision
        return this.montoTotal
    }





}