package com.sunglasses.linglingcalculator.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.sunglasses.linglingcalculator.R
import com.sunglasses.linglingcalculator.utils.UpdateInfo

class UpdateDialog(context: Context) : Dialog(context) {

    private lateinit var progressBar: ProgressBar
    private lateinit var tvTitle: TextView
    private lateinit var tvMessage: TextView
    private lateinit var btnAction: Button
    private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_update)

        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvMessage = findViewById<TextView>(R.id.tvMessage)
        btnAction = findViewById<Button>(R.id.btnAction)
        btnCancel = findViewById<Button>(R.id.btnCancel)

        btnCancel.setOnClickListener { dismiss() }
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    fun setLoading(message: String = "正在檢查更新...") {
        progressBar.visibility = View.VISIBLE
        tvMessage.text = message
        btnAction.visibility = View.GONE
    }

    fun showUpdateAvailable(updateInfo: UpdateInfo, onUpdateClick: () -> Unit) {
        progressBar.visibility = View.GONE
        tvTitle.text = "發現新版本！"
        tvMessage.text = """
            當前版本：${updateInfo.currentVersion}
            最新版本：${updateInfo.latestVersion}
            
            建議更新到最新版本以獲得更好的體驗。
        """.trimIndent()
        btnAction.text = "前往更新"
        btnAction.visibility = View.VISIBLE
        btnAction.setOnClickListener { onUpdateClick() }
    }

    fun showLatest(updateInfo: UpdateInfo, onRepoClick: () -> Unit) {
        progressBar.visibility = View.GONE
        tvTitle.text = "已是最新版本"
        tvMessage.text = """
            當前版本：${updateInfo.currentVersion}
            
            您正在使用最新版本，無需更新。
        """.trimIndent()
        btnAction.text = "前往GitHub"
        btnAction.visibility = View.VISIBLE
        btnAction.setOnClickListener { onRepoClick() }
    }

    fun showError(message: String) {
        progressBar.visibility = View.GONE
        tvTitle.text = "檢查失敗"
        tvMessage.text = message
        btnAction.text = "關閉"
        btnAction.visibility = View.VISIBLE
        btnAction.setOnClickListener { dismiss() }
    }
}