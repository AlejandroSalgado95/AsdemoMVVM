package com.example.asdemoapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.asdemoapp.R
import com.example.asdemoapp.databinding.FragmentApiDetailedInfoBinding
import com.example.asdemoapp.domain.model.ApiCategoryModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApiDetailedInfoFragment : BaseFragment() {

    private var _binding: FragmentApiDetailedInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentApiCategoryModel: ApiCategoryModel

    private val args by navArgs<ApiDetailedInfoFragmentArgs>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApiDetailedInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentApiCategoryModel = args.currentApiCategoryModel

        with(binding) {
            toolbar.title = currentApiCategoryModel.api
            toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            apiTextDetails.text = currentApiCategoryModel.api
            if (!currentApiCategoryModel.auth.isNullOrEmpty()) {
                authTextDetails.text = currentApiCategoryModel.auth
                authLabel.visibility = View.VISIBLE
                authTextDetails.visibility = View.VISIBLE
            }

            categoryTextDetails.text = currentApiCategoryModel.category
            corsTextDetails.text = currentApiCategoryModel.cors
            descriptionTextDetails.text = currentApiCategoryModel.description
            httpsTextDetails.text = currentApiCategoryModel.https.toString()
            linkTextDetails.text = currentApiCategoryModel.link
            linkTextDetails.setOnClickListener { onOpenUrlLinkClicked(binding.linkTextDetails.text.toString()) }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}