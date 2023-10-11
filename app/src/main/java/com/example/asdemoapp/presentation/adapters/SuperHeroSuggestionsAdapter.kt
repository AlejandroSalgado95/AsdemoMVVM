package com.example.asdemoapp.presentation.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.asdemoapp.R
import com.example.asdemoapp.domain.model.SuperHeroModel
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.internal.managers.ViewComponentManager
import javax.inject.Inject

class SuperHeroSuggestionsAdapter @Inject constructor(
    private val myContext: Context,
    private val glide : RequestManager
) : ArrayAdapter<SuperHeroModel>(myContext, R.layout.item_super_hero_suggestion) {

    val inflater = myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val filteredsuperheroes: MutableList<SuperHeroModel> = mutableListOf()
    private var allSuperHeroes: MutableList<SuperHeroModel> = mutableListOf()


    fun setSuperheroes(superheroes : List<SuperHeroModel>){
        filteredsuperheroes.clear()
        allSuperHeroes.clear()
        filteredsuperheroes.addAll(superheroes)
        allSuperHeroes.addAll(superheroes)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return filteredsuperheroes.size
    }

    override fun getItem(position: Int): SuperHeroModel {
        return filteredsuperheroes[position]
    }

    override fun getItemId(position: Int): Long {
        val id = filteredsuperheroes[position].id ?: "0"
        return id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val myConvertView = inflater.inflate(R.layout.item_super_hero_suggestion, parent, false)


        try {
            val superhero: SuperHeroModel = getItem(position)
            val superHeroId = myConvertView?.findViewById<View>(R.id.superhero_id) as TextView
            val superHeroAvatar =
                myConvertView.findViewById<View>(R.id.avatar) as ShapeableImageView
            superHeroId.text = superhero.id
            glide.load(superhero.images?.sm).transition(
                DrawableTransitionOptions.withCrossFade()).into(superHeroAvatar)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return myConvertView
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun convertResultToString(resultValue: Any): String {
                 return (resultValue as SuperHeroModel).id ?: "No id"
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    val superHeroSuggestions: MutableList<SuperHeroModel> = ArrayList()
                    for (superHero in allSuperHeroes) {
                        val condition =
                            superHero.id?.lowercase()?.startsWith(constraint.toString().lowercase())
                                ?: false
                        if (condition) {
                            superHeroSuggestions.add(superHero)
                        }
                    }
                    filterResults.values = superHeroSuggestions
                    filterResults.count = superHeroSuggestions.size
                }
                return filterResults
            }

            override fun publishResults(
                constraint: CharSequence?,
                results: FilterResults
            ) {
                filteredsuperheroes.clear()
                if (results.count > 0) {
                    for (result in results.values as List<*>) {
                        if (result is SuperHeroModel) {
                            filteredsuperheroes.add(result)
                        }
                    }
                    notifyDataSetChanged()
                } else if (constraint == null) {
                    filteredsuperheroes.addAll(allSuperHeroes)
                    notifyDataSetInvalidated()
                }
            }
        }
    }
}
