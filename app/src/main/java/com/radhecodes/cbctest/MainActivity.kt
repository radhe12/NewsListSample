package com.radhecodes.cbctest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
import com.radhecodes.cbctest.utils.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

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
        binding.swipeToRefresh.setColorSchemeResources(R.color.purple_200, R.color.purple_200, R.color.purple_200 )
        initList()
        fetchNews()
        newsViewModel.getNewsFromLocalDb().observe(this, Observer {
            if (it.isNotEmpty()) {
                recyclerAdapter.submitList(it)
                val types = getTypes(it)
                for (type in types) {
                    val chip = Chip(this)
                    chip.text = type
                    types_chip.addView(chip)
                }
            } else {
            //TODO() no data template
            }
        })
    }

    private fun fetchNews() {
        newsViewModel.getNewsFromApi().observe(this, Observer { status ->
            if(status?.status == StatusType.SUCCESS) {
                binding.noNetworkTemplate.visibility = View.GONE
                binding.chipsScroll.visibility = View.VISIBLE
                binding.newsList.visibility = View.VISIBLE
                val list = status.isInstanceOf<List<ApiResponseItem>>()
                if (list != null) {
                    newsList = list.map { News(it.getNewsId()!!, it.getTitle()!!, it.getImageUrl()!!, it.getPublishedTime()!!, it.getType()!!) }
                }
                newsViewModel.deleteAndInsertAllNews(newsList)
                if (binding.swipeToRefresh.isRefreshing) {
                    binding.swipeToRefresh.isRefreshing = false
                }
            } else {
                    //TODO() error toast
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

    private fun getNewsFromType(type: String, newsList: List<ApiResponseItem>?): List<ApiResponseItem?>? {
        return newsList?.filter { it.getType() == type }
    }

    override fun onRefresh() {
        newsList = emptyList()
        fetchNews()
    }

}