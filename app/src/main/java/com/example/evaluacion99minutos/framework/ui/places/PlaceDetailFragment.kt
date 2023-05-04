package com.example.evaluacion99minutos.framework.ui.places

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.evaluacion99minutos.R
import com.example.evaluacion99minutos.databinding.FragmentPlaceDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlaceDetailFragment : Fragment() {

    private lateinit var viewModel: PlaceDetailViewModel
    private var _binding: FragmentPlaceDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val EXTRA_PLACE_ID = "placeId"
        const val EXTRA_PLACE_NAME = "placeName"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[PlaceDetailViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonSave.setOnClickListener {
                val name = editTextTitle.text.toString()
                val description = editTextDescription.text.toString()
                viewModel.save(name, description)
                findNavController().navigate(PlaceDetailFragmentDirections.actionplaceDetailFragmentToPlacesFragment())
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.state.collect {
                        editTextTitle.setText(it.name)
                        editTextDescription.setText(it.description)
                    }
                }
            }
        }
    }

}