package com.example.effectivemobiletesttask.presentation.RecyclerViewBlockRecommendations

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobiletesttask.R
import com.example.effectivemobiletesttask.domain.pojo.Offers

class RVBlockRecommendationsAdapter(private var items: List<Offers> = listOf()) :
    RecyclerView.Adapter<RVBlockRecommendationsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_rv_block_recommendations)
        val textView1: TextView = itemView.findViewById(R.id.text1_rv_block_recommendations)
        val textView2: TextView = itemView.findViewById(R.id.text2_rv_block_recommendations)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_block_recommendations, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        when (item.id) {
            "near_vacancies" -> {
                holder.imageView.setImageResource(R.drawable.ic_ellipse_blue)
            }

            "level_up_resume" -> {
                holder.imageView.setImageResource(R.drawable.ic_ellipse_green_star)
            }

            "temporary_job" -> {
                holder.imageView.setImageResource(R.drawable.ic_ellipse_green_notepad)
            }

            else -> {
                holder.imageView.setImageDrawable(null)
            }
        }
        holder.imageView
        holder.textView1.apply {
            this.text = item.title?.trim()//удалить лишние пробелы вначале
            this.background = null
            if (item.button?.text == null) {
                this.maxLines = 3
            } else {
                this.maxLines = 2
            }

        }
        holder.textView2.apply {
            this.text = item.button?.text
            this.background = null
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<Offers>) {
        this.items = newItems
        notifyDataSetChanged()//знаю, что лучше не использовать, но в данном случае можно, так как
        //элементов мало
    }
}