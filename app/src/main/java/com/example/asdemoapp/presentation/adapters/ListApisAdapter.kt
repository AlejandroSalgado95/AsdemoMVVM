package com.example.asdemoapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.asdemoapp.R
import com.example.asdemoapp.databinding.ItemApiCategoryBinding
import com.example.asdemoapp.domain.model.ApiCategoryModel
import com.example.asdemoapp.domain.model.SuperHeroModel
import javax.inject.Inject

class ListApisAdapter @Inject constructor(
    private val context: Context
) : ListAdapter<ApiCategoryModel, ListApisAdapter.ApiCategoryViewHolder>(ApiCategoryModelDiff) {


    private var onItemClickListener: ((ApiCategoryModel, Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (ApiCategoryModel, Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiCategoryViewHolder {
        return ApiCategoryViewHolder(
            ItemApiCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ApiCategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ApiCategoryViewHolder(private val binding: ItemApiCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ApiCategoryModel) = with(binding) {
            apiText.text = context.getString(R.string.api_template, item.api)
            descriptionText.text = item.description
            categoryChip.text = item.category

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(item, adapterPosition)
            }
        }
    }
}

object ApiCategoryModelDiff : DiffUtil.ItemCallback<ApiCategoryModel>() {
    override fun areContentsTheSame(oldItem: ApiCategoryModel, newItem: ApiCategoryModel): Boolean {
        return when {
            !oldItem.api.equals(newItem.api) -> false
            !oldItem.description.equals(newItem.description) -> false
            !oldItem.auth.equals(newItem.auth) -> false
            !oldItem.cors.equals(newItem.cors) -> false
            !oldItem.link.equals(newItem.link) -> false
            !oldItem.category.equals(newItem.category) -> false
            !oldItem.https != newItem.https -> false
            else -> true
        }
    }

    override fun areItemsTheSame(oldItem: ApiCategoryModel, newItem: ApiCategoryModel): Boolean {
        return oldItem.api.equals(newItem.api)
    }
}
