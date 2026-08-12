package com.sunglasses.linglingcalculator.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.sunglasses.linglingcalculator.R

class AboutDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_about)

        val ivAvatar = findViewById<ImageView>(R.id.ivAvatar)
        val tvGithubLink = findViewById<TextView>(R.id.tvGithubLink)
        val btnClose = findViewById<Button>(R.id.btnClose)
        
        ivAvatar.setImageResource(R.drawable.ic_github)

        tvGithubLink.text = "Github仓库"
        tvGithubLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Sunglasses-MaxBin/Lingling-Calculator"))
            context.startActivity(intent)
        }

        btnClose.setOnClickListener { dismiss() }
    }
}