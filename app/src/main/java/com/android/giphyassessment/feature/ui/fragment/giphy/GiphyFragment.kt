package com.android.giphyassessment.feature.ui.fragment.giphy

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.giphyassessment.database.AppDao
import com.android.giphyassessment.database.AppDatabase
import com.android.giphyassessment.databinding.FragmentGiphyBinding
import com.android.giphyassessment.feature.shared.adapters.GiphyAdapter
import com.android.giphyassessment.feature.shared.base.BaseFragment
import com.android.giphyassessment.feature.shared.model.GiphyModel
import com.android.giphyassessment.utils.PaginationScrollListener
import com.android.giphyassessment.utils.extensions.viewGone
import com.android.giphyassessment.utils.extensions.viewVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer
import java.util.TimerTask

/**
 * Created by Aashis on 05,September,2023
 */
@AndroidEntryPoint
class GiphyFragment : BaseFragment<FragmentGiphyBinding, GiphyViewModel>(),
    SwipeRefreshLayout.OnRefreshListener {

    private val giphyViewModel: GiphyViewModel by viewModels()
    override fun getViewBinding() = FragmentGiphyBinding.inflate(layoutInflater)
    override fun getViewModel(): GiphyViewModel = giphyViewModel
    private var giphyList = mutableListOf<GiphyModel>()
    private lateinit var layoutManager: LinearLayoutManager
    lateinit var giphyAdapter: GiphyAdapter
    private var totalPages = 1
    private var perPage = 1
    private var isLoading = false
    private var isLastPage = false
    private val pageStart = 1
    private var currentPage = pageStart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        initRecyclerView()
        context?.let { giphyViewModel.getGiphy(it, 1) }
        initObserver()
        binding?.srlSwipeRefresh?.setOnRefreshListener(this)
    }

    private fun initObserver() {
        giphyViewModel.eventList.observe(viewLifecycleOwner) { response ->

            if (response != null) {
                if (!isLoading) {
                    response?.data?.let { giphyList.addAll(it) }
                    var totalPages = response.pagination.total_count
                    var perPage = response.pagination.count
                    var questionList = response.data
                    if (!questionList.isNullOrEmpty()) {

                        if (totalPages != null) {
                            this.totalPages = totalPages
                            this.perPage = perPage ?: 1

                            giphyAdapter.clearAll()
                            giphyAdapter.addAll(questionList)

                            if (currentPage < totalPages)
                                giphyAdapter.addLoadingFooter()
                            else {
                                isLastPage = true
                            }
                        }
                    }
                    giphyAdapter.notifyDataSetChanged()
                } else {
                    giphyAdapter.removeLoadingFooter()
                    isLoading = false
                    giphyAdapter.addAll(response?.data)
                    giphyAdapter.notifyDataSetChanged()

                    if (currentPage < this.totalPages)
                        giphyAdapter.addLoadingFooter()
                    else {
                        isLastPage = true
                        giphyAdapter.removeLoadingFooter()
                    }
                }
            }
        }

        giphyViewModel.loading.observe(viewLifecycleOwner) {
            if (it == true) {
                binding?.prbGiphy.viewVisible()
            } else {
                binding?.prbGiphy.viewGone()
                if (binding?.srlSwipeRefresh?.isRefreshing == true)
                    binding?.srlSwipeRefresh?.isRefreshing = false
            }
        }
    }

    override fun onRefresh() {
        context?.let { giphyViewModel.getGiphy(it, 1) }
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        giphyAdapter = GiphyAdapter(context, giphyList) { position ->

            if (giphyList[position].isFav == true) {
                context?.let { giphyViewModel.deleteGiphyById(it, giphyList[position].id ?: "") }
                giphyList[position].isFav = false
            } else {
                context?.let { giphyViewModel.insertData(it, giphyList[position]) }
                giphyList[position].isFav = true
            }

            giphyAdapter.notifyItemChanged(position)
        }
        binding?.rcvGiphy?.layoutManager = layoutManager
        binding?.rcvGiphy?.adapter = giphyAdapter
        binding?.rcvGiphy?.addOnScrollListener(object :
            PaginationScrollListener(layoutManager!!) {
            override fun getTotalPageCount(): Int = totalPages
            override fun getPerPageCount(): Int = perPage
            override fun isLastPage(): Boolean = isLastPage

            override fun isLoading(): Boolean = isLoading

            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                Handler().postDelayed(
                    { context?.let { giphyViewModel.getGiphy(it, currentPage) } },
                    1500
                )
            }

            override fun isFabSeen(): Boolean = true

            override fun showFabIcon() {}

            override fun hideFabIcon() {}
        })
    }

    private fun initTextChangeListener() {
        binding?.edtSearch?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (!p0.isNullOrEmpty()) {


                } else {

                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }
}