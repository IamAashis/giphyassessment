package com.android.giphyassessment.feature.shared.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.giphyassessment.R
import com.android.giphyassessment.databinding.AdapterGiphyBinding
import com.android.giphyassessment.databinding.AdapterPaginationBinding
import com.android.giphyassessment.feature.shared.model.GiphyModel
import com.android.giphyassessment.feature.shared.viewHolder.GiphyVH
import com.android.giphyassessment.feature.shared.viewHolder.PaginationVH
import com.android.giphyassessment.utils.constants.GiphyConstants
import com.bumptech.glide.Glide

/**
 * Created by Aashis on 06,September,2023
 */
class GiphyAdapter(
    var context: Context?,
    private var giphyList: MutableList<GiphyModel>,
    private val onQuizClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var itemChecked: Boolean? = false
    private var isLoadingAdded = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            GiphyConstants.giphyItemSection -> {
                GiphyVH(
                    AdapterGiphyBinding.inflate(
                        LayoutInflater.from(parent.context), parent,
                        false
                    )
                )
                {
                    onQuizClicked(it)
                }
            }

            else -> {
                PaginationVH(
                    AdapterPaginationBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            GiphyConstants.giphyItemSection -> {
                val binding = (holder as GiphyVH).binding
                val giphyItems = giphyList[position]

                context?.let {
                    Glide.with(it)
                        .load(giphyItems.images?.downsized_medium?.url)
                        .into(binding.imvGiphy)
                }

                if (giphyItems.isFav == true) {
                    binding.imbFav.setImageDrawable(context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.ic_favorite
                        )
                    })
                } else {
                    binding.imbFav.setImageDrawable(context?.let {
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.ic_unfavorite
                        )
                    })
                }

            }

            else -> {
            }
        }
    }

    override fun getItemCount(): Int = giphyList.size

    fun clearAll() {
        isLoadingAdded = false
        giphyList.clear()
        notifyDataSetChanged()
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(GiphyModel(types = "loading"))
    }

    fun addAll(generalQuizList: List<GiphyModel>?) {
        if (generalQuizList != null) {
            for (result in generalQuizList) {
                add(result)
            }
        }
    }

    fun add(r: GiphyModel) {
        giphyList.add(r)
        notifyItemInserted(giphyList.size - 1)
    }

    /*   override fun getItemViewType(position: Int): Int {
           return if (generalQuizList[position].type == "quiz") {
               CategoryConstants.favItemSection
           } else {
               CategoryConstants.loadingSection
           }
       }*/

    override fun getItemViewType(position: Int): Int {
        return when (giphyList[position].types) {
            GiphyConstants.giphy -> GiphyConstants.giphyItemSection
            else -> GiphyConstants.loadingSection
        }
    }


    fun removeLoadingFooter() {

        isLoadingAdded = false

        val position = giphyList.size - 1
        val result = getItem(position)

        if (giphyList[position].types == "loading") {
            if (result != null) {
                giphyList?.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    fun getItem(position: Int): GiphyModel? {
        return giphyList?.get(position)
    }
}