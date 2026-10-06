package com.sunglasses.linglingcalculator.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
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

        // 1. 设置窗口背景透明（去掉外面那层难看的白框）
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        // 2. 核心修复：动态适配屏幕宽度
        window?.let { window ->
            val displayMetrics = context.resources.displayMetrics
            val screenWidth = displayMetrics.widthPixels
            // 弹窗宽度设为屏幕宽度的 85%，并保持高度自适应
            window.setLayout((screenWidth * 0.85).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
            window.setGravity(Gravity.CENTER)
        }

        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvMessage = findViewById<TextView>(R.id.tvMessage)
        btnAction = findViewById<Button>(R.id.btnAction)
        btnCancel = findViewById<Button>(R.id.btnCancel)

        btnCancel.setOnClickListener { dismiss() }
        setCancelable(false)
        setCanceledOnTouchOutside(false)

        // 初始化时直接显示加载中状态
        progressBar.visibility = View.VISIBLE
        tvMessage.text = "正在检查更新..."
        btnAction.visibility = View.GONE
    }

    // 后面的 setLoading、showUpdateAvailable 等方法保持不变...
    fun setLoading(message: String = "正在检查更新...") {
        progressBar.visibility = View.VISIBLE
        tvMessage.text = message
        btnAction.visibility = View.GONE
    }

    fun showUpdateAvailable(updateInfo: UpdateInfo, onUpdateClick: () -> Unit) {
        progressBar.visibility = View.GONE
        tvTitle.text = "发现新版本！"
        tvMessage.text = """
            当前版本：${updateInfo.currentVersion}
            最新版本：${updateInfo.latestVersion}
            
            建议更新到最新版本以获得更好的体验。
        """.trimIndent()
        btnAction.text = "前往更新"
        btnAction.visibility = View.VISIBLE
        btnAction.setOnClickListener { onUpdateClick() }
    }

    fun showLatest(updateInfo: UpdateInfo, onRepoClick: () -> Unit) {
        progressBar.visibility = View.GONE
        tvTitle.text = "已是最新版本"
        tvMessage.text = """
            当前版本：${updateInfo.currentVersion}
            
            您正在使用最新版本，无需更新。
        """.trimIndent()
        btnAction.text = "前往GitHub"
        btnAction.visibility = View.VISIBLE
        btnAction.setOnClickListener { onRepoClick() }
    }

    fun showError(message: String) {
        progressBar.visibility = View.GONE
        tvTitle.text = "检查失败"
        tvMessage.text = message
        btnAction.text = "关闭"
        btnAction.visibility = View.VISIBLE
        btnAction.setOnClickListener { dismiss() }
    }
}