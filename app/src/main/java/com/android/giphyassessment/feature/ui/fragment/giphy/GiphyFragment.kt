package com.android.giphyassessment.feature.ui.fragment.giphy

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.android.giphyassessment.databinding.FragmentGiphyBinding
import com.android.giphyassessment.feature.shared.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Aashis on 05,September,2023
 */
@AndroidEntryPoint
class GiphyFragment : BaseFragment<FragmentGiphyBinding, GiphyViewModel>() {

    private val giphyViewModel: GiphyViewModel by viewModels()
    override fun getViewBinding() = FragmentGiphyBinding.inflate(layoutInflater)

    override fun getViewModel(): GiphyViewModel = giphyViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {

    }
}