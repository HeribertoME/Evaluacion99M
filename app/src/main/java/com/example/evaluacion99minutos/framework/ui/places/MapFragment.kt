package com.example.evaluacion99minutos.framework.ui.places

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.evaluacion99minutos.R
import com.example.evaluacion99minutos.databinding.FragmentMapBinding
import com.example.evaluacion99minutos.framework.ui.commons.toast
import com.example.evaluacion99minutos.framework.ui.utils.AlertDialogUtils
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MapFragment : Fragment(),
    OnMapReadyCallback, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnMyLocationClickListener {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private var mMap: GoogleMap? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocationManager: LocationManager? = null
    private var mMyLocation: LatLng? = null
    private var mIsLoadedMapFirstTime = true
    private var mMarker: Marker? = null
    private var mCountDownTimer: CountDownTimer? = null
    private val mHandler: Handler = Handler()
    private var mRunnable: Runnable? = null
    private lateinit var mPlacesClient: PlacesClient
    private lateinit var mGeoCoder: Geocoder
    private var mAddresses: List<Address>? = null
    private var mCurrentAddress: String? = ""

    private val locationPermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        var areAllGranted = true

        for (isGranted in result.values) {
            Log.d("PERMISSIONS", "permissionMultiple isGranted: $isGranted")
            areAllGranted = areAllGranted && isGranted
        }

        if (areAllGranted) {
            startMapConfig()
        } else {
            Log.d("PERMISSIONS", "Algunos permisos fueron denegados")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        val fineLocation = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocation = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)

        if (fineLocation != PackageManager.PERMISSION_GRANTED) {
            locationPermissions.launch(permissions)
        } else if (coarseLocation != PackageManager.PERMISSION_GRANTED) {
            locationPermissions.launch(permissions)
        }

        mLocationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.ivConfirmLocation.isEnabled = false
        if (mLocationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {
            startMapConfig()
        } else {
            launchAlertToGpsState()
        }
        binding.ivConfirmLocation.setOnClickListener {
            val latitude = mMyLocation?.latitude ?: 0.0
            val longitude = mMyLocation?.longitude ?: 0.0
            Log.d(MapFragment::class.simpleName, "Current address: $mCurrentAddress")
            val direction = PlacesFragmentDirections.actionPlacesFragmentToPlaceDetailFragment()
            direction.placeName = mCurrentAddress ?: ""
            findNavController().navigate(direction)
        }
    }

    private fun startMapConfig() {
        mGeoCoder = Geocoder(requireActivity(), Locale.getDefault())
        startMapView()
        startAutoCompletePlaces()
    }

    private fun startMapView() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mRunnable = Runnable {
            mapFragment.getMapAsync(this)
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        }
        mHandler.postDelayed(mRunnable!!, 1_000)
    }

    private fun startAutoCompletePlaces() {
        if (!Places.isInitialized()) {
            Places.initialize(requireActivity(), getString(R.string.google_maps_key))
        }

        mPlacesClient = Places.createClient(requireActivity())

        val autoCompleteFragment = childFragmentManager
            .findFragmentById(R.id.autocomplete) as AutocompleteSupportFragment
        autoCompleteFragment.setPlaceFields(
            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        )
        autoCompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(status: Status) {
                AlertDialogUtils.Builder(requireActivity())
                    .setTitle(getString(R.string.text_alert))
                    .setMessage(getString(R.string.error_message))
                    .setAccept(getString(R.string.text_alert_accept)) {}
                    .showAlert()
            }

            override fun onPlaceSelected(place: Place) {
                setAddress(place.latLng!!)
                mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(place.latLng!!, 14.0f))
            }

        })
    }

    private fun launchAlertToGpsState() {
        AlertDialogUtils.Builder(requireActivity())
            .setTitle(getString(R.string.text_alert))
            .setMessage(getString(R.string.text_alert_gps_not_activate))
            .setAccept(getString(R.string.text_alert_accept)) {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivityForResult(intent, 3000)
            }
            .setCancel(getString(R.string.text_alert_cancel)) {
                requireActivity().onBackPressed()
            }
            .showAlert()
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        startMapConfiguration()
    }

    override fun onCameraMoveStarted(idReason: Int) {
        if (GoogleMap.OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION == idReason && mIsLoadedMapFirstTime) {
            mCountDownTimer = object : CountDownTimer(3000, 1000) {
                override fun onTick(p0: Long) {
                    // Empty
                }

                override fun onFinish() {
                    mIsLoadedMapFirstTime = false
                }

            }
            mCountDownTimer?.start()
        }
    }

    override fun onMyLocationClick(location: Location) {
        setAddress(LatLng(location.latitude, location.longitude))
    }

    private fun startMapConfiguration() {
        if (ActivityCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        mMap?.isMyLocationEnabled = true
        mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap?.isTrafficEnabled = false
        mMap?.setOnCameraMoveStartedListener(this)
        mMap?.setOnMyLocationClickListener(this)

        if (mMyLocation != null) {
            mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(mMyLocation!!, 14.0f))
            setAddress(mMyLocation!!)
        } else {
            mFusedLocationClient?.lastLocation?.addOnSuccessListener(requireActivity()) {
                if (it != null) {
                    mMyLocation = LatLng(it.latitude, it.longitude)
                    mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(mMyLocation!!, 14.0f))
                    setAddress(mMyLocation!!)
                }
            }
        }
    }

    private fun setAddress(location: LatLng) {
        try {
            mAddresses = mGeoCoder.getFromLocation(location.latitude, location.longitude, 1)
            mMyLocation = location
            mCurrentAddress = mAddresses?.get(0)?.getAddressLine(0)
            binding.tvMapAddress.text = mCurrentAddress
            mMarker = if (mMarker != null) {
                mMarker?.remove()
                mMap?.addMarker(MarkerOptions().position(location))
            } else {
                mMap?.addMarker(MarkerOptions().position(location))
            }
        } catch (e: Exception) {
            requireActivity().toast(getString(R.string.map_location_error))
            mCurrentAddress = ""
        } finally {
            binding.ivConfirmLocation.isEnabled = true
        }
    }

    override fun onDestroyView() {
        removeFragmentsMap()
        super.onDestroyView()
        if (mCountDownTimer != null) {
            mCountDownTimer?.cancel()
            mCountDownTimer = null
        }
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable!!)
        }
    }

    /**
     * Method to remove map and autocomplete fragment
     */
    private fun removeFragmentsMap() {
        try {
            val fragment =
                childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            val fragmentAutocomplete =
                childFragmentManager.findFragmentById(R.id.autocomplete) as AutocompleteSupportFragment
            val ft = requireActivity().supportFragmentManager.beginTransaction()
            ft.remove(fragment)
            ft.remove(fragmentAutocomplete)
            ft.commit()
        } catch (e: Exception) {
            // Empty catch
        }
    }

}