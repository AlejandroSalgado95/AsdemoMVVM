package com.example.asdemoapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.asdemoapp.R
import com.example.asdemoapp.databinding.FragmentProfileBinding
import com.example.asdemoapp.databinding.FragmentSuperHeroDetailedInfoBinding
import com.example.asdemoapp.domain.model.SuperHeroModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuperHeroDetailedInfoFragment : BaseFragment() {

    private var _binding: FragmentSuperHeroDetailedInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentSuperHeroModel: SuperHeroModel
    private val args by navArgs<SuperHeroDetailedInfoFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuperHeroDetailedInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentSuperHeroModel = args.currentSuperHeroModel
        with(binding) {
            toolbar.title = currentSuperHeroModel.name
            toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            toolbar.setNavigationOnClickListener() {
                findNavController().navigateUp()
            }
            nameText.text = currentSuperHeroModel.name
            slugText.text = currentSuperHeroModel.slug
            intelligenceText.text = currentSuperHeroModel.powerstats?.intelligence.toString()
            strengthText.text = currentSuperHeroModel.powerstats?.strength.toString()
            speedText.text = currentSuperHeroModel.powerstats?.speed.toString()
            durabilityText.text = currentSuperHeroModel.powerstats?.durability.toString()
            powerText.text = currentSuperHeroModel.powerstats?.power.toString()
            combatText.text = currentSuperHeroModel.powerstats?.combat.toString()
            genderText.text = currentSuperHeroModel.appearance?.gender.toString()
            raceText.text = currentSuperHeroModel.appearance?.race.toString()
            heightText.text = currentSuperHeroModel.appearance?.height?.get(1).toString()
            weightText.text = currentSuperHeroModel.appearance?.weight?.get(1).toString()
            Glide.with(avatar).load(currentSuperHeroModel.images?.sm).into(avatar)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
