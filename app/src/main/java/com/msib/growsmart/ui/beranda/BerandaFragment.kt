package com.msib.growsmart.ui.beranda

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.gms.location.LocationServices
import com.msib.growsmart.R
import com.msib.growsmart.databinding.FragmentBerandaBinding
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.ui.factory.ViewModelFactory
import com.msib.growsmart.ui.login.LoginActivity
import com.msib.growsmart.utils.dataStore
import kotlinx.coroutines.launch


class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!
    private val berandaViewModel by viewModels<BerandaViewModel> {
        ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
    }
    private lateinit var locationManager: LocationManager
    private lateinit var preference: UserPreference
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preference = UserPreference.getInstance(requireContext().dataStore)

        val mFusedLocation = LocationServices.getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocation.lastLocation.addOnSuccessListener { location ->
            with(binding) {
                berandaViewModel.getWeather(location.longitude, location.latitude)
                berandaViewModel.getWeather.observe(viewLifecycleOwner) { data ->
                    tvTanggal.text = data.currentWeather.city
                    tvWeather.text = data.currentWeather.weatherDescription
                    Glide.with(requireContext())
                        .load(data.currentWeather.weatherIcon)
                        .into(ivWeather)
                }
            }

            Log.d(
                "My Current location",
                "Lat : ${location?.latitude} Long : ${location?.longitude}"
            )
            // Display in Toast
            Toast.makeText(
                requireContext(),
                "Lat : ${location?.latitude} Long : ${location?.longitude}",
                Toast.LENGTH_LONG
            ).show()


        }


        initObserver()
    }

    private fun initObserver() {
        with(binding) {
            berandaViewModel.getUser().observe(viewLifecycleOwner) {
                if(it.isLogin) {
                    tvCuaca.text = "Hey, ${it.name}"
                }
            }


            ivProfile.setOnClickListener {
                lifecycleScope.launch {
                    preference.logout()
                }
                LoginActivity.start(requireContext())
            }
        }
    }


}