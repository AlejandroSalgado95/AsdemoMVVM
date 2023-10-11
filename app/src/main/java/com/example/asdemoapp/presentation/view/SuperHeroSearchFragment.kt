package com.example.asdemoapp.presentation.view

import android.os.Bundle
import android.view.*
import android.view.View.OnTouchListener
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.asdemoapp.R
import com.example.asdemoapp.databinding.FragmentSuperHeroSearchBinding
import com.example.asdemoapp.presentation.adapters.ListSuperHeroesAdapter
import com.example.asdemoapp.presentation.adapters.SuperHeroSuggestionsAdapter
import com.example.asdemoapp.presentation.viewmodel.SuperHeroSearchFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SuperHeroSearchFragment : BaseFragment() {

    private var _binding: FragmentSuperHeroSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SuperHeroSearchFragmentViewModel by viewModels ()

    @Inject
    lateinit var suggestionsAdapter : SuperHeroSuggestionsAdapter

    @Inject
    lateinit var listSuperHeroesAdapter: ListSuperHeroesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listSuperHeroesAdapter.setOnItemClickListener { item, adapterPosition ->
            findNavController().navigate(
                SuperHeroSearchFragmentDirections.actionSuperHeroSearchToSuperHeroDetailedInfoFragment(
                    item
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuperHeroSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = getString(R.string.super_hero_search_title)
        setupRecyclerView(binding.rvSuperHero)
        binding.rvSuperHero.adapter = listSuperHeroesAdapter
        binding.autoCompleteTv.setAdapter(suggestionsAdapter)

        viewModel.superHeroCacheList.observe(viewLifecycleOwner) { superHeroCacheList ->
            val superHeroSuggestions = superHeroCacheList ?: listOf()
            suggestionsAdapter.setSuperheroes(superHeroSuggestions)
        }

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SuperHeroSearchFragmentViewModel.UiState.IsLoading -> {
                    showLoading(
                        binding.loading,
                        binding.rvSuperHero,
                        binding.errorViewContainer,
                        binding.nothingFoundContainer
                    )
                }
                is SuperHeroSearchFragmentViewModel.UiState.IsError -> {
                    showErrorUI(
                        binding.loading,
                        binding.rvSuperHero,
                        binding.errorViewContainer,
                        binding.nothingFoundContainer
                    )
                }
                is SuperHeroSearchFragmentViewModel.UiState.Data -> {

                    if (state.data != null) {
                        val mySuperHero = state.data
                        listSuperHeroesAdapter.submitList(listOf(mySuperHero))
                        binding.rvSuperHero.scheduleLayoutAnimation()
                        showList(
                            binding.loading,
                            binding.rvSuperHero,
                            binding.errorViewContainer,
                            binding.nothingFoundContainer
                        )
                    } else {
                        showNothingFound(
                            binding.loading,
                            binding.rvSuperHero,
                            binding.errorViewContainer,
                            binding.nothingFoundContainer
                        )
                    }
                }
            }
        }
        
        binding.errorViewContainer.reloadBtn.setOnClickListener {
            val query = binding.autoCompleteTv.text
            query?.let {
                viewModel.getSuperHero(it.toString())
            }
        }

        binding.autoCompleteTv.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                viewModel.getSuperHero(binding.autoCompleteTv.text.toString())
                binding.autoCompleteTv.text.clear()
            }
            false
        }

        //noinspection AndroidLintClickableViewAccessibility
        binding.autoCompleteTv.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= binding.autoCompleteTv.right - binding.autoCompleteTv.compoundDrawables[DRAWABLE_RIGHT].bounds.width()
                ) {
                    binding.autoCompleteTv.text.clear()
                    return@OnTouchListener true
                }
            }
            false
        })
    }

    override fun onDestroyView() {
        requireView().findViewById<RecyclerView>(R.id.rv_super_hero).adapter = null
        requireView().findViewById<AutoCompleteTextView>(R.id.auto_complete_tv).setAdapter(null)
        _binding = null
        super.onDestroyView()
    }

}