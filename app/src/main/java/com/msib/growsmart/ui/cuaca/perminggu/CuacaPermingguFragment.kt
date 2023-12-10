package com.msib.growsmart.ui.cuaca.perminggu

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationServices
import com.msib.growsmart.R
import com.msib.growsmart.databinding.FragmentCuacaPermingguBinding
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.WeeklyWeatherItem
import com.msib.growsmart.ui.factory.ViewModelFactory

class CuacaPermingguFragment : Fragment() {
    private var _binding: FragmentCuacaPermingguBinding? = null
    private val binding get() = _binding!!
    private lateinit var cuacaPermingguAdapter: CuacaPermingguAdapter
    private val cuacaPermingguViewModel by viewModels<CuacaPermingguViewModel>{
        ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
    }
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCuacaPermingguBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        initView()
        mFused()
        filterCurrentWeather()
    }

    private fun mFused() {
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
                cuacaPermingguViewModel.getListHourlyWeather(token, location.longitude, location.latitude)
                cuacaPermingguViewModel.getWeeklyWeather.observe(viewLifecycleOwner) { list ->
                    showHourlyList(list)
                }
                cuacaPermingguViewModel.isLoading.observe(viewLifecycleOwner) {
                    showLoading(it)
                }
            }

            Log.d(
                "My Current location",
                "Lat : ${location?.latitude} Long : ${location?.longitude}"
            )

        }
    }

    private fun showLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }

    private fun initObserver() {
        cuacaPermingguViewModel.getUser().observe(viewLifecycleOwner) {
            if(it.isLogin) {
                token = it.token
            }
        }
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvWeekly.layoutManager = layoutManager
    }

    private fun showHourlyList(data : List<WeeklyWeatherItem>) {
        with(binding) {
            cuacaPermingguAdapter = CuacaPermingguAdapter(data)
            rvWeekly.adapter= cuacaPermingguAdapter
        }
    }

    private fun filterCurrentWeather() {
        with(binding) {
            ivFilter.setOnClickListener {
                findNavController().navigate(R.id.navigation_cuaca)
            }
        }
    }

}