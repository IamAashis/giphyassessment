package com.android.giphyassessment.feature.ui.fragment.favourite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.giphyassessment.databinding.FragmentFavouriteBinding
import com.android.giphyassessment.feature.shared.adapters.FavAdapter
import com.android.giphyassessment.feature.shared.base.BaseFragment
import com.android.giphyassessment.feature.shared.model.GiphyModel
import com.android.giphyassessment.utils.extensions.viewGone
import com.android.giphyassessment.utils.extensions.viewVisible
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Aashis on 05,September,2023
 */
@AndroidEntryPoint
class FavouriteFragment : BaseFragment<FragmentFavouriteBinding, FavouriteViewModel>() {

    private val favouriteViewModel: FavouriteViewModel by viewModels()
    override fun getViewBinding() = FragmentFavouriteBinding.inflate(layoutInflater)
    override fun getViewModel(): FavouriteViewModel = favouriteViewModel
    private lateinit var favAdapter: FavAdapter
    private var giphyList = mutableListOf<GiphyModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        initRecyclerView()
        initObserver()
        getFavData()
    }

    fun getFavData() {
        favouriteViewModel.getGiphyData(context)
    }

    private fun initObserver() {
        favouriteViewModel.giphyList.observe(viewLifecycleOwner) { response ->
            if (!response.isNullOrEmpty()) {
                giphyList.clear()
                giphyList.addAll(response)
                favAdapter.notifyDataSetChanged()
                binding?.txvGifs.viewGone()
            } else {
                binding?.txvGifs.viewVisible()
            }
        }

        favouriteViewModel.loading.observe(viewLifecycleOwner) {
            if (it == true) {
                binding?.prbGiphy.viewVisible()
            } else {
                binding?.prbGiphy.viewGone()
            }
        }
    }

    private fun initRecyclerView() {
        val layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        favAdapter = FavAdapter(requireContext(), giphyList) { position ->
            context?.let { favouriteViewModel.deleteGiphyById(it, giphyList[position].id ?: "") }
            giphyList.removeAt(position)
            favAdapter.notifyDataSetChanged()
        }
        binding?.rcvGiphy?.layoutManager = layoutManager
        binding?.rcvGiphy?.adapter = favAdapter
    }
}