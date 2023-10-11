package com.example.asdemoapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.asdemoapp.R
import com.example.asdemoapp.databinding.FragmentApiDetailedInfoBinding
import com.example.asdemoapp.databinding.FragmentApiListBinding
import com.example.asdemoapp.presentation.adapters.ListApisAdapter
import com.example.asdemoapp.presentation.viewmodel.ApiListFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ApiListFragment : BaseFragment() {

    private var _binding: FragmentApiListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ApiListFragmentViewModel by viewModels ()

    @Inject
    lateinit var listApisAdapter: ListApisAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listApisAdapter.setOnItemClickListener { item, adapterPosition ->
            findNavController().navigate(ApiListFragmentDirections.actionListToDetails(item))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApiListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = getString(R.string.list_title)
        setupRecyclerView(binding.rvApicategory)
        setupListeners()
        binding.rvApicategory.adapter = listApisAdapter
        if (viewModel.uiState.value == null)
            viewModel.getApiCategories()

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            binding.swipeRefreshLayout.isRefreshing = false
            when (state) {
                is ApiListFragmentViewModel.UiState.IsLoading -> {
                    showLoading(
                        binding.loading,
                        binding.rvApicategory,
                        binding.errorViewContainer,
                        binding.nothingFoundContainer
                    )
                }
                is ApiListFragmentViewModel.UiState.IsError -> {
                    showErrorUI(
                        binding.loading,
                        binding.rvApicategory,
                        binding.errorViewContainer,
                        binding.nothingFoundContainer
                    )
                }
                is ApiListFragmentViewModel.UiState.Data -> {
                    state.data?.let { myList ->
                        if (myList.isNotEmpty()) {
                            listApisAdapter.submitList(myList)
                            binding.rvApicategory.scheduleLayoutAnimation()
                            showList(
                                binding.loading,
                                binding.rvApicategory,
                                binding.errorViewContainer,
                                binding.nothingFoundContainer
                            )
                        } else {
                            showNothingFound(
                                binding.loading,
                                binding.rvApicategory,
                                binding.errorViewContainer,
                                binding.nothingFoundContainer
                            )
                        }
                    }

                }
            }
        }

        binding.errorViewContainer.reloadBtn.setOnClickListener {
            viewModel.getApiCategories()
        }
    }

    private fun setupListeners() {
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.brand_color)
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            viewModel.getApiCategories()
        }
    }

    override fun onDestroyView() {
        requireView().findViewById<RecyclerView>(R.id.rv_apicategory).adapter = null
        _binding = null
        super.onDestroyView()
    }
}