package com.android.giphyassessment.feature.ui.fragment.giphy

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.giphyassessment.databinding.FragmentGiphyBinding
import com.android.giphyassessment.feature.shared.adapters.GiphyAdapter
import com.android.giphyassessment.feature.shared.base.BaseFragment
import com.android.giphyassessment.feature.shared.model.GiphyModel
import com.android.giphyassessment.utils.PaginationScrollListener
import com.android.giphyassessment.utils.extensions.viewGone
import com.android.giphyassessment.utils.extensions.viewVisible
import dagger.hilt.android.AndroidEntryPoint

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
    private lateinit var giphyAdapter: GiphyAdapter
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
        initTextChangeListener()
        getTrendingData()
        initObserver()
        initListener()
    }

    private fun initListener() {
        binding?.srlSwipeRefresh?.setOnRefreshListener(this)
    }

    fun getTrendingData() {
        context?.let { giphyViewModel.getGiphy(it, 1) }
    }

    private fun initObserver() {
        giphyViewModel.giphyList.observe(viewLifecycleOwner) { response ->

            if (response != null) {
                if (!isLoading) {
                    response.data?.let { giphyList.addAll(it) }
                    val totalPages = response.pagination.total_count
                    val perPage = response.pagination.count
                    val questionList = response.data
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
                    giphyAdapter.addAll(response.data)
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
            if (it == true && !isLoading) {
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
            PaginationScrollListener(layoutManager) {
            override fun getTotalPageCount(): Int = totalPages
            override fun getPerPageCount(): Int = perPage
            override fun isLastPage(): Boolean = isLastPage

            override fun isLoading(): Boolean = isLoading

            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                Handler(Looper.getMainLooper()).postDelayed(
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
                    context?.let {
                        giphyViewModel.searchGiphy(
                            1,
                            binding?.edtSearch?.text.toString(),
                        )
                    }
                } else {
                    context?.let { giphyViewModel.getGiphy(it, 1) }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }
}