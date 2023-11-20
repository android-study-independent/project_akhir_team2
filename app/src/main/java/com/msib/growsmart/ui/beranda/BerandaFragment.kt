package com.msib.growsmart.ui.beranda

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.gms.location.LocationServices
import com.msib.growsmart.databinding.FragmentBerandaBinding
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.ui.factory.ViewModelFactory
import com.msib.growsmart.ui.login.LoginActivity
import com.msib.growsmart.utils.Constant
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date


class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!
    private val berandaViewModel by viewModels<BerandaViewModel> {
        ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
    }
    private lateinit var preference: UserPreference
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Constant.LOCATION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, proceed with location-related tasks
                } else {
                    // Permission denied, handle accordingly
                }
            }
        }
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
            if(location != null){
                with(binding) {
                    berandaViewModel.getWeather(location.longitude, location.latitude)
                    berandaViewModel.getWeather.observe(viewLifecycleOwner) { data ->

                        val date = Date ()
                        val dayOfWeek = DateFormat.format ("EEEE", date)
                        val month = DateFormat.format ("MMM", date)
                        val c = Calendar.getInstance()
                        val dayNum = c.get(Calendar.DAY_OF_MONTH)
                        val year = c.get(Calendar.YEAR)


                        tvTanggal.text = "$dayOfWeek, $dayNum $month $year"
                        tvKota.text = data.currentWeather.city
                        tvWeather.text = data.currentWeather.weatherDescription
                        tvSuhu.text = " ${data.currentWeather.temperature}℃ "
                        tvKelembapan.text = "Kelembapan ${data.currentWeather.humidity}%"
                        Glide.with(requireContext())
                            .load(data.currentWeather.weatherIcon)
                            .into(ivWeather)
                    }
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