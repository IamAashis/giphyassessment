package com.android.giphyassessment.feature.shared.viewHolder

import com.android.giphyassessment.databinding.AdapterFavouriteBinding
import com.android.giphyassessment.feature.shared.base.BaseViewHolder

/**
 * Created by Aashis on 07,September,2023
 */

class FavouriteVH(
    val binding: AdapterFavouriteBinding,
    private val onUserClicked: (position: Int) -> Unit
) : BaseViewHolder(binding.root) {
    init {
        binding.imbFav.setOnClickListener {
            onUserClicked(adapterPosition)
        }
    }
}