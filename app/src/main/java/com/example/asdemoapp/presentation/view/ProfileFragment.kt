package com.example.asdemoapp.presentation.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.asdemoapp.R
import com.example.asdemoapp.data.remote.PublicApisApi
import com.example.asdemoapp.databinding.FragmentEditProfilePhotoDialogBinding
import com.example.asdemoapp.databinding.FragmentProfileBinding
import com.example.asdemoapp.domain.repository.PublicApisRepository
import com.example.asdemoapp.presentation.viewmodel.ProfileFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileFragmentViewModel by viewModels ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.photoUri.observe(viewLifecycleOwner){
            binding.profileImage.setImageURI(it)
        }
        with(binding) {
            toolbar.title = getString(R.string.profile_title)
            accountText.setOnClickListener { onOpenUrlLinkClicked("https://github.com/${binding.accountText.text}") }
            emailText.setOnClickListener { onOpenEmail() }
            editButton.setOnClickListener { onOpenBottomSheetDialog() }
        }
    }

    private fun onOpenEmail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData(Uri.parse("mailto:"))
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(binding.emailText.text.toString()))
        requireActivity().startActivity(intent)
    }

    private fun onOpenBottomSheetDialog() {
        val dialogFragment = EditProfilePhotoDialogFragment()
        dialogFragment.show(childFragmentManager,"DIALOG")
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}