package com.sunglasses.linglingcalculator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sunglasses.linglingcalculator.R

class SettingsAdapter(
    private val onAppearanceClick: () -> Unit,
    private val onAboutClick: () -> Unit
) : RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

    private val settings = listOf(
        SettingItem("外观设置", "调整浅色/深色主题", R.drawable.ic_palette),
        SettingItem("关于本应用", "开发者信息和Github链接", R.drawable.ic_info)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_setting, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(settings[position])
    }

    override fun getItemCount() = settings.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)

        fun bind(setting: SettingItem) {
            ivIcon.setImageResource(setting.iconRes)
            tvTitle.text = setting.title
            tvDescription.text = setting.description
            itemView.setOnClickListener {
                when (adapterPosition) {
                    0 -> onAppearanceClick()
                    1 -> onAboutClick()
                }
            }
        }
    }

    data class SettingItem(
        val title: String,
        val description: String,
        val iconRes: Int
    )
}