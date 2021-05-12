package com.radhecodes.cbctest

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.chip.Chip
import com.radhecodes.cbctest.adapters.NewsListAdapter
import com.radhecodes.cbctest.databinding.ActivityMainBinding
import com.radhecodes.cbctest.repository.model.News
import com.radhecodes.cbctest.ui.NewsViewModel
import com.radhecodes.cbctest.utils.NetworkStatus
import com.radhecodes.cbctest.utils.TopSpacingItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private val newsViewModel: NewsViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    lateinit var recyclerAdapter: NewsListAdapter
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
        } else {
            binding.chipsScroll.visibility = View.GONE
            binding.newsList.visibility = View.GONE
            binding.noNetworkTemplate.visibility = View.VISIBLE
        }

     setupObserver()
    }

    private fun setupObserver() {
        newsViewModel.news.observe(this){ result ->
            if(!result.data.isNullOrEmpty()) {
                newsList = result.data
                recyclerAdapter.submitList(result.data)
                newsViewModel.typeChips.value = getTypes(result.data)

            } else {
                Toast.makeText(this, "No latest news available, Please try again later", Toast.LENGTH_LONG).show()
            }
        }

        newsViewModel.selectedChip.observe(this, {
            binding.typesChip.check(it.second)
            recyclerAdapter.submitList(getNewsFromType(it.first, newsList))
        })

        newsViewModel.typeChips.observe(this, {types ->
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
                        newsViewModel.selectedChip.value = Pair(chip.text.toString(), chip.id)
                    } ?: kotlin.run {
                        recyclerAdapter.submitList(newsList)
                    }
                }
            } else {
                binding.typesChip.visibility = View.GONE
            }
        })
    }

    private fun initList() {
        binding.newsList.apply {
          layoutManager = LinearLayoutManager(this@MainActivity)
          recyclerAdapter = NewsListAdapter()
          val topSpacingItemDecoration = TopSpacingItemDecoration(50)
          addItemDecoration(topSpacingItemDecoration)
          adapter = recyclerAdapter
        }
    }

    private fun getTypes(newsList: List<News>): List<String> {
        return newsList.map{ it.type }.toSet().toList()
    }

    private fun getNewsFromType(type: String, newsList: List<News>): List<News>{
        return newsList.filter { it.type.toUpperCase(Locale.ROOT) == type }
    }

    override fun onRefresh() {
        newsList = emptyList()
    }

}