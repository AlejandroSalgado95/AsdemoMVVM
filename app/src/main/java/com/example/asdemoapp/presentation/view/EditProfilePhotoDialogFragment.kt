package com.example.asdemoapp.presentation.view

import android.Manifest
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.asdemoapp.databinding.FragmentApiDetailedInfoBinding
import com.example.asdemoapp.databinding.FragmentEditProfilePhotoDialogBinding
import com.example.asdemoapp.presentation.viewmodel.ProfileFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditProfilePhotoDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentEditProfilePhotoDialogBinding? = null
    private val binding get() = _binding!!
    private var requestMultiplePermissions: ActivityResultLauncher<Array<String>>? = null
    private var requestCameraImage: ActivityResultLauncher<Uri>? = null
    private var requestGalleryImage: ActivityResultLauncher<String>? = null
    private var genericUri: Uri? = null
    private val viewModel: ProfileFragmentViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerIntentLaunchers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfilePhotoDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.galleryOption.setOnClickListener { requestGalleryImage?.launch("image/*") }
        binding.photoOption.setOnClickListener {
            requestMultiplePermissions?.launch(
                arrayOf(
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        }
    }

    private fun registerIntentLaunchers() {
        registerCameraLauncher { success ->
            if (success) {
                genericUri?.let { viewModel.setPhotoUri(it) }
            } else {
                val resolver = requireContext().contentResolver
                genericUri?.let {
                    resolver.delete(it, null, null);
                }
            }
        }
        registerGalleryLauncher { uri -> uri?.let { viewModel.setPhotoUri(it) } }
        registerPermissionsLauncher { resultsMap ->
            var hasGrantedAll = true
            resultsMap.forEach {
                Log.d("PERMISSION", "${it.key}, granted: ${it.value}")
                if (!it.value) {
                    hasGrantedAll = false
                }
            }
            if (hasGrantedAll) {
                genericUri = createBlankImageFile()
                genericUri?.let {
                    requestCameraImage?.launch(it)
                }
            }
        }
    }

    private fun registerPermissionsLauncher(callback: (Map<String, @JvmSuppressWildcards Boolean>) -> Unit) {
        requestMultiplePermissions = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            callback(it) }
    }

    private fun registerCameraLauncher(callback: (Boolean) -> Unit) {
        requestCameraImage = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            callback(it)
        }
    }

    private fun registerGalleryLauncher(callback: (Uri?) -> Unit) {
        requestGalleryImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            callback(it)
        }
    }

    private fun createBlankImageFile(): Uri? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val resolver = requireContext().contentResolver

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, timeStamp)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/asdemo_pictures")
            } else {
                Environment.getExternalStoragePublicDirectory("${Environment.DIRECTORY_DCIM}/asdemo_pictures")
            }
        }
        return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}