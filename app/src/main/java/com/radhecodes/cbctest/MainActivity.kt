package com.radhecodes.cbctest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.radhecodes.cbctest.adapters.NewListAdapter
import com.radhecodes.cbctest.repository.model.ApiResponseItem
import com.radhecodes.cbctest.ui.NewsViewModel
import com.radhecodes.cbctest.utils.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val newsViewModel: NewsViewModel by viewModel()
    lateinit var recyclerAdapter: NewListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initList()
        newsViewModel.getNews().observe(this, Observer {
            recyclerAdapter.submitList(it!!.toList())
            val types = getTypes(it.toList())
            for(type in types!!) {
                val chip = Chip(this)
                chip.text = type
                types_chip.addView(chip)
            }
        })

        var lastCheckedId = View.NO_ID
        types_chip.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == View.NO_ID) {
                group.check(lastCheckedId)
            }
            lastCheckedId = checkedId
        }

    }

    private fun initList() {
        news_list.apply {
          layoutManager = LinearLayoutManager(this@MainActivity)
          recyclerAdapter = NewListAdapter()
          val topSpacingItemDecoration = TopSpacingItemDecoration(30)
          addItemDecoration(topSpacingItemDecoration)
          adapter = recyclerAdapter
        }
    }

    private fun getTypes(newsList: List<ApiResponseItem>?): List<String?>? {
        return newsList?.map{ it.getType() }?.toSet()?.toList()
    }

    private fun getNewsFromType(type: String, newsList: List<ApiResponseItem>?): List<ApiResponseItem?>? {
        return newsList?.filter { it.getType() == type }
    }

}