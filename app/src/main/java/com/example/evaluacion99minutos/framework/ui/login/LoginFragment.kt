package com.example.evaluacion99minutos.framework.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.evaluacion99minutos.R
import com.example.evaluacion99minutos.databinding.FragmentLoginBinding
import com.example.evaluacion99minutos.framework.ui.commons.observe
import com.example.evaluacion99minutos.framework.ui.commons.toast
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.viewModel = viewModel

        lifecycle.addObserver(viewModel)
        observe(viewModel.viewState, ::onViewState)

        return binding.root
    }

    private fun onViewState(state: LoginViewState?) {
        when (state) {
            LoginViewState.LoginError -> {
                requireActivity().toast("Error al iniciar sesión")
            }
            LoginViewState.LoginSuccess -> {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToPlacesFragment()
                )
            }
            else -> {}
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}