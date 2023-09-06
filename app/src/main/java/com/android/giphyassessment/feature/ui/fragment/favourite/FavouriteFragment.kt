package com.android.giphyassessment.feature.ui.fragment.favourite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.android.giphyassessment.databinding.FragmentFavouriteBinding
import com.android.giphyassessment.feature.shared.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Aashis on 05,September,2023
 */
@AndroidEntryPoint
class FavouriteFragment : BaseFragment<FragmentFavouriteBinding, FavouriteViewModel>() {

    private val favouriteViewModel: FavouriteViewModel by viewModels()
    override fun getViewBinding() = FragmentFavouriteBinding.inflate(layoutInflater)
    override fun getViewModel(): FavouriteViewModel = favouriteViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {

    }
}