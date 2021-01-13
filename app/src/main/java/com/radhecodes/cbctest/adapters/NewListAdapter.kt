package com.radhecodes.cbctest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.radhecodes.cbctest.databinding.NewsListItemBinding
import com.radhecodes.cbctest.repository.model.News

class NewListAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<News>() {

        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.getNewsId() == newItem.getNewsId()
        }

        override fun areContentsTheSame(
            oldItem: News,
            newItem: News
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(
          binding
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

    fun submitList(list: List<News>?) {
        differ.submitList(list)
    }

    inner class NewsViewHolder(val binding: NewsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: News) = with(itemView) {

            binding.newsTitle.text = item.getTitle()
            binding.newPublishDate.text = item.getPublishedTime()

            Glide.with(itemView.context)
                .load(item.getImageUrl())
                .into(binding.newsThumbnail)
        }
    }
}