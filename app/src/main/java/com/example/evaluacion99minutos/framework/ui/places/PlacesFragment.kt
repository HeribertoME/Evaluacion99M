package com.example.evaluacion99minutos.framework.ui.places

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.evaluacion99minutos.R
import com.example.evaluacion99minutos.databinding.FragmentPlacesBinding

/**
 * A simple [Fragment] subclass.
 * Use the [PlacesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlacesFragment : Fragment() {

    private var mActualFragment: Fragment? = null
    private var mMapFragment = MapFragment()
    private var mListFragment = ListFragment()

    private var _binding: FragmentPlacesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlacesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mActualFragment == null) {
            commit(mMapFragment, mListFragment)
        } else {
            turnOnOff()
        }

        binding.tvMap.setOnClickListener {
            commit(mMapFragment, mListFragment)
        }

        binding.tvList.setOnClickListener {
            commit(mListFragment, mMapFragment)
        }
    }

    private fun commit(fragment: Fragment, removedFragment: Fragment) {
        if (mActualFragment == null || mActualFragment?.javaClass?.simpleName != fragment.javaClass.simpleName) {
            childFragmentManager.beginTransaction().remove(removedFragment).commitAllowingStateLoss()
            childFragmentManager.beginTransaction().replace(R.id.flPlaces, fragment)
                .commitAllowingStateLoss()
            mActualFragment = fragment
        }
        turnOnOff()
    }

    private fun turnOnOff() {
        if (mActualFragment?.javaClass?.simpleName == MapFragment::class.java.simpleName) {
            changeStyleNavigation(binding.tvMap, binding.tvList)
        } else {
            changeStyleNavigation(binding.tvList, binding.tvMap)
        }
    }

    private fun changeStyleNavigation(optionSelected: TextView, optionUnselected: TextView) {
        val textColorOptionSelected = ContextCompat.getColor(requireContext(), R.color.white)
        optionSelected.setTextColor(textColorOptionSelected)

        val textColorOptionUnselected = ContextCompat.getColor(requireContext(), R.color.black)
        optionUnselected.setTextColor(textColorOptionUnselected)

        optionSelected.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_selected)
        optionUnselected.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_unselected)
    }

}