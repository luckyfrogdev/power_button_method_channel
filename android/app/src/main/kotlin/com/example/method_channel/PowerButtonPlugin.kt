package com.example.method_channel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.PowerManager
import io.flutter.plugin.common.MethodChannel

class PowerButtonPlugin(private val channel: MethodChannel, private val context: Context) {
    private val powerManager: PowerManager by lazy {
        context.getSystemService(Context.POWER_SERVICE) as PowerManager
    }

    init {
        registerReceiver()
    }


    fun registerReceiver() {
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
        }
        context.registerReceiver(PowerButtonReceiver(), filter)
    }

    fun unregisterReceiver() {
        context.unregisterReceiver(PowerButtonReceiver())
    }


    inner class PowerButtonReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                Intent.ACTION_SCREEN_ON -> {
                    println("intent.action: ${intent.action}")
                    println("Screen turned on")
                    // Screen turned on
                    // Handle power button press event here
                    sendPowerButtonEvent("power_on")
                }

                Intent.ACTION_SCREEN_OFF -> {
                    println("intent.action: ${intent.action}")
                    println("Screen turned off")
                    // Screen turned off
                    // Handle power button press event here
                    sendPowerButtonEvent("power_off")
                }
            }
        }
    }

    private fun sendPowerButtonEvent(type: String) {
        println("sendPowerButtonEvent")
        channel.invokeMethod("powerButtonPressed", type)
        val wakeLock: PowerManager.WakeLock = powerManager.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                "com.example.method_channel.PowerButtonPlugin:WakeLock"
        )

        wakeLock.acquire(10 * 60 * 1000L /*10 minutes*/)
        wakeLock.release()
    }


}
