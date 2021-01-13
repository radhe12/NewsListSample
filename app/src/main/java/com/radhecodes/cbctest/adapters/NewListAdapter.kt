package com.radhecodes.cbctest.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.radhecodes.cbctest.R
import com.radhecodes.cbctest.repository.model.ApiResponseItem
import kotlinx.android.synthetic.main.news_list_item.view.*

class NewListAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ApiResponseItem>() {

        override fun areItemsTheSame(oldItem: ApiResponseItem, newItem: ApiResponseItem): Boolean {
            return oldItem.getNewsId() == newItem.getNewsId()
        }

        override fun areContentsTheSame(
            oldItem: ApiResponseItem,
            newItem: ApiResponseItem
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.news_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NewsViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<ApiResponseItem>) {
        differ.submitList(list)
    }

    inner class NewsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(item: ApiResponseItem) = with(itemView) {

            itemView.news_title.text = item.getTitle()
            itemView.new_publish_date.text = item.getPublishedTime()

            Glide.with(itemView.context)
                .load(item.getImageUrl())
                .into(itemView.news_thumbnail)
        }
    }
}