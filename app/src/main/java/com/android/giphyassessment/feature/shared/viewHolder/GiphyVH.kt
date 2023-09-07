package com.android.giphyassessment.feature.shared.viewHolder

import com.android.giphyassessment.databinding.AdapterGiphyBinding
import com.android.giphyassessment.feature.shared.base.BaseViewHolder

/**
 * Created by Aashis on 06,September,2023
 */
class GiphyVH(
    val binding: AdapterGiphyBinding,
    private val onUserClicked: (position: Int) -> Unit
) : BaseViewHolder(binding.root) {
    init {
        binding.imbFav.setOnClickListener {
            onUserClicked(adapterPosition)
        }
    }
}