package com.example.clientapp


import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.aidlrandomservice.IMyAidlInterface


class MainActivity : AppCompatActivity() {

    private var randomService: IMyAidlInterface? = null
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            randomService = IMyAidlInterface.Stub.asInterface(service)
            isBound = true
            Toast.makeText(this@MainActivity, "Service connected", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
            randomService = null
            Toast.makeText(this@MainActivity, "Service disconnected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.textView)
        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            if (isBound && randomService != null) {
                try {
                    val number = randomService!!.randomNumber
                    textView.text = "Random number: $number"
                } catch (e: RemoteException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Remote call failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Service not bound", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Bind to the service
        val intent = Intent("com.example.aidlrandomservice.IMyAidlInterface")
        intent.setPackage("com.example.aidlrandomservice")   // package name of the service app
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }
}
