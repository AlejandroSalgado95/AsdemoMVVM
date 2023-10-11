package com.example.asdemoapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.asdemoapp.databinding.ItemSuperHeroBinding
import com.example.asdemoapp.domain.model.SuperHeroModel
import javax.inject.Inject


class ListSuperHeroesAdapter @Inject constructor(
    private val glide: RequestManager
) : ListAdapter<SuperHeroModel, ListSuperHeroesAdapter.SuperHeroViewHolder>(SuperHeroModelDiff) {


    private var onItemClickListener: ((SuperHeroModel, Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (SuperHeroModel, Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        return SuperHeroViewHolder(
            ItemSuperHeroBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SuperHeroViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SuperHeroViewHolder(private val binding: ItemSuperHeroBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SuperHeroModel) = with(binding) {
            nameText.text = item.name
            slugText.text = item.slug
            val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

            glide.load(item.images?.sm).transition(DrawableTransitionOptions.withCrossFade(factory)).into(profileImage)
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(item, adapterPosition)
            }
        }
    }
}

object SuperHeroModelDiff : DiffUtil.ItemCallback<SuperHeroModel>() {
    override fun areContentsTheSame(oldItem: SuperHeroModel, newItem: SuperHeroModel): Boolean {

        val oldItemPowerStats = oldItem.powerstats
        val newItemPowerStats = newItem.powerstats
        val oldItemImages = oldItem.images
        val newItemImages = newItem.images

        return when {
            !oldItem.name.equals(newItem.name) -> false
            !oldItem.slug.equals(newItem.slug) -> false
            !(oldItemPowerStats?.intelligence == newItemPowerStats?.intelligence) -> false
            !(oldItemPowerStats?.strength == newItemPowerStats?.strength) -> false
            !(oldItemPowerStats?.speed == newItemPowerStats?.speed) -> false
            !(oldItemPowerStats?.durability == newItemPowerStats?.durability) -> false
            !(oldItemPowerStats?.power == newItemPowerStats?.power) -> false
            !(oldItemPowerStats?.combat == newItemPowerStats?.combat) -> false
            !oldItemImages?.sm.equals(newItemImages?.sm) -> false
            else -> true
        }
    }

    override fun areItemsTheSame(oldItem: SuperHeroModel, newItem: SuperHeroModel): Boolean {
        return oldItem.id == newItem.id
    }
}
