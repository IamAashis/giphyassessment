package com.android.giphyassessment.feature.shared.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.giphyassessment.R
import com.android.giphyassessment.databinding.AdapterFavouriteBinding
import com.android.giphyassessment.feature.shared.base.BaseAdapter
import com.android.giphyassessment.feature.shared.model.GiphyModel
import com.android.giphyassessment.feature.shared.viewHolder.FavouriteVH
import com.bumptech.glide.Glide

/**
 * Created by Aashis on 07,September,2023
 */

class FavAdapter(
    var context: Context,
    var favList: MutableList<GiphyModel>,
    private val onItemClicked: (position: Int) -> Unit
) : BaseAdapter<FavouriteVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavouriteVH(
        AdapterFavouriteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) { onItemClicked(it) }

    override fun onBindViewHolder(holder: FavouriteVH, position: Int) {
        val favItems = favList[position]
        val binding = holder.binding
        binding.imvGiphy.clipToOutline = true

        Glide.with(context)
            .load(favItems.images?.downsized_medium?.url)
            .placeholder(R.drawable.ic_placeholder)
            .into(binding.imvGiphy)
    }

    override fun getItemCount(): Int = favList.size
}