package com.example.taskapp.ui.profile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.taskapp.databinding.FragmentProfileBinding
import com.example.taskapp.utils.Preferences

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val getContent = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { imageUri: Uri? ->
        if (imageUri != null) {
            binding.civProfile.setImageURI(imageUri)
            val preferences = Preferences(requireContext())
            preferences.setPrefImage(imageUri.toString())
        }
    }


    companion object {
        const val MIMETYPE_IMAGES = "image/*"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
    }

    private fun initListeners() {
        binding.civProfile.setOnClickListener {
            getContent.launch(MIMETYPE_IMAGES)
        }

        binding.btnSaveProfile.setOnClickListener{
            //Сохранение в SharedPreferences
            val preferences = Preferences(requireContext())
            preferences.setPrefTitle(binding.edtProfile.text.toString())
            Toast.makeText(requireContext(), "saving", Toast.LENGTH_LONG).show()
        }
    }

    private fun initViews() {
        val preferences = Preferences(requireContext())

        //Если данные из SharedPreferences не null тогда их применим
        if (preferences.getPrefTitle() != "") {
            binding.edtProfile.setText(
                preferences.getPrefTitle()
            )
        }
        try {
            Log.d("ololo", preferences.getPrefImage().toString())
            if (preferences.getPrefImage() != "") {
                Glide.with(this).load(preferences.getPrefImage()).into(binding.civProfile)
            }
//            binding.civProfile.setImageURI(Uri.parse(preferences.getPrefImage().toString()))
        } catch (e: java.lang.Exception) {
            Log.d("ololo", "Exeption ${e.message.toString()}")
        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
