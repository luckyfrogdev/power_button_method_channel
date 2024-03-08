package com.example.method_channel

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {

    private lateinit var channel: MethodChannel
    private lateinit var powerManager: PowerManager
    private lateinit var powerButtonPlugin: PowerButtonPlugin


    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "power_button")
        PowerButtonPlugin(channel, this) // Pass 'this' as the activity instance

        powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        powerButtonPlugin = PowerButtonPlugin(channel, this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        powerButtonPlugin.registerReceiver()

    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the receiver when the activity is destroyed
        powerButtonPlugin.unregisterReceiver()
    }

}
