package com.example.instanttest

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.gms.instantapps.InstantApps
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /**
     * Intent to launch after the app has been installed.
     */
    private val postInstallIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://install-api.instantappsample.com/")
    ).addCategory(Intent.CATEGORY_BROWSABLE).putExtras(Bundle().apply {
        putString("The key to", "sending data via intent")
    })
    private val REFERRER = "InstallApiActivity"
    private val REQUEST_CODE = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkIfIsInstantApp()
        initBtnInstallListener()
    }

    private fun checkIfIsInstantApp() {
        val isInstantApp = InstantApps.getPackageManagerCompat(this).isInstantApp
        tvInstantAppEnable?.let {
            if (isInstantApp) {
                it.text = "Running as InstantApp"
                it.setTextColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.colorInstantAppEnable
                    )
                )
            } else {
                it.text = "Running as regular app"
                it.setTextColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.colorInstantAppDisable
                    )
                )
            }
        }
    }

    private fun initBtnInstallListener() {
        btnInstall?.setOnClickListener {
            InstantApps.showInstallPrompt(
                this,
                postInstallIntent,
                REQUEST_CODE,
                REFERRER
            )
        }
    }
}
