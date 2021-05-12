package com.radhecodes.cbctest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.radhecodes.cbctest.databinding.NewsListItemBinding
import com.radhecodes.cbctest.repository.model.News

class NewsListAdapter : ListAdapter<News, RecyclerView.ViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(
          binding
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NewsViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class NewsViewHolder(private val binding: NewsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: News) = with(itemView) {

            binding.newsTitle.text = item.title
            binding.newPublishDate.text = item.publishedTime

            Glide.with(itemView.context)
                .load(item.imageUrl)
                .into(binding.newsThumbnail)
        }
    }
}


 class NewsDiffCallback : DiffUtil.ItemCallback<News>() {

    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.newsId == newItem.newsId
    }

    override fun areContentsTheSame(
            oldItem: News,
            newItem: News
    ): Boolean {
        return oldItem == newItem
    }

}