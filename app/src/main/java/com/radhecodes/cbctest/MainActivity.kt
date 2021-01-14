package com.radhecodes.cbctest

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.chip.Chip
import com.radhecodes.cbctest.adapters.NewListAdapter
import com.radhecodes.cbctest.databinding.ActivityMainBinding
import com.radhecodes.cbctest.repository.model.ApiResponseItem
import com.radhecodes.cbctest.repository.model.News
import com.radhecodes.cbctest.repository.model.StatusType
import com.radhecodes.cbctest.ui.NewsViewModel
import com.radhecodes.cbctest.utils.NetworkStatus
import com.radhecodes.cbctest.utils.TopSpacingItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private val newsViewModel: NewsViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    lateinit var recyclerAdapter: NewListAdapter
    private var newsList: List<News> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.swipeToRefresh.setOnRefreshListener(this)
        binding.swipeToRefresh.setColorSchemeResources(
            R.color.purple_200,
            R.color.purple_200,
            R.color.purple_200
        )
        initList()

        if(newsList.isEmpty() && NetworkStatus.isInternetAvailable(this)) {
            binding.chipsScroll.visibility = View.VISIBLE
            binding.newsList.visibility = View.VISIBLE
            binding.noNetworkTemplate.visibility = View.GONE
            fetchNews()
        } else {
            binding.chipsScroll.visibility = View.GONE
            binding.newsList.visibility = View.GONE
            binding.noNetworkTemplate.visibility = View.VISIBLE
        }
        newsViewModel.getNewsFromLocalDb().observe(this, {
            if (it.isNotEmpty()) {
                recyclerAdapter.submitList(it)
                val types = getTypes(it)
                if (types.isNotEmpty()) {
                    binding.typesChip.visibility = View.VISIBLE
                    binding.typesChip.removeAllViews()
                    for (type in types) {
                        val chip = Chip(this)
                        chip.id = ViewCompat.generateViewId()
                        chip.isCheckable = true
                        chip.isClickable = true
                        chip.text = type.toUpperCase(Locale.ROOT)
                        binding.typesChip.addView(chip)
                    }
                    binding.typesChip.setOnCheckedChangeListener { group, checkedId ->
                        val chip: Chip? = group.findViewById(checkedId)
                        chip?.let {
                          recyclerAdapter.submitList(getNewsFromType(chip.text.toString(), newsList))
                        } ?: kotlin.run {
                            recyclerAdapter.submitList(newsList)
                        }
                    }
                } else {
                    binding.typesChip.visibility = View.GONE
                }
            } else {
                Toast.makeText(this, "No latest news available, Please try again later", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun fetchNews() {
        newsViewModel.getNewsFromApi().observe(this, { status ->
            if (status?.status == StatusType.SUCCESS) {
                binding.noNetworkTemplate.visibility = View.GONE
                binding.chipsScroll.visibility = View.VISIBLE
                binding.newsList.visibility = View.VISIBLE
                val list = status.isInstanceOf<List<ApiResponseItem>>()
                if (list != null) {
                    newsList = list.map {
                        News(
                            it.getNewsId()!!,
                            it.getTitle()!!,
                            it.getImageUrl()!!,
                            it.getPublishedTime()!!,
                            it.getType()!!
                        )
                    }
                }
                newsViewModel.deleteAndInsertAllNews(newsList)
                if (binding.swipeToRefresh.isRefreshing) {
                    binding.swipeToRefresh.isRefreshing = false
                }
            } else if(status?.status == StatusType.NETWORK_ERROR) {
                Toast.makeText(this, "Please connect to internet to get latest news", Toast.LENGTH_LONG).show()
                if (binding.swipeToRefresh.isRefreshing) {
                    binding.swipeToRefresh.isRefreshing = false
                }
            } else {
                Toast.makeText(this, "Error fetching latest news, Please try again later", Toast.LENGTH_LONG).show()
                if (binding.swipeToRefresh.isRefreshing) {
                    binding.swipeToRefresh.isRefreshing = false
                }
            }
        })
    }

    private fun initList() {
        binding.newsList.apply {
          layoutManager = LinearLayoutManager(this@MainActivity)
          recyclerAdapter = NewListAdapter()
          val topSpacingItemDecoration = TopSpacingItemDecoration(50)
          addItemDecoration(topSpacingItemDecoration)
          adapter = recyclerAdapter
        }
    }

    private fun getTypes(newsList: List<News>): List<String> {
        return newsList.map{ it.getType() }.toSet().toList()
    }

    private fun getNewsFromType(type: String, newsList: List<News>): List<News>{
        return newsList.filter { it.getType().toUpperCase(Locale.ROOT) == type }
    }

    override fun onRefresh() {
        newsList = emptyList()
        fetchNews()
    }

}