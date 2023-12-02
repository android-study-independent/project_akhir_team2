package com.msib.growsmart.ui.cuaca.perjam

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationServices
import com.msib.growsmart.R
import com.msib.growsmart.databinding.FragmentCuacaPerjamBinding
import com.msib.growsmart.response.HourlyWeatherItem


class CuacaPerjamFragment : Fragment() {
    private var _binding: FragmentCuacaPerjamBinding? = null
    private val binding get() = _binding!!
    private lateinit var cuacaPerjamAdapter: CuacaPerjamAdapter
    private val cuacaPerjamViewModel by viewModels<CuacaPerjamViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCuacaPerjamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        mFused()
        filterWeeklyWeather()
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
                cuacaPerjamViewModel.getListHourlyWeather(location.longitude, location.latitude)
                cuacaPerjamViewModel.getHourlyWeather.observe(viewLifecycleOwner) { list ->
                    showHourlyList(list)
                }
                cuacaPerjamViewModel.isLoading.observe(viewLifecycleOwner) {
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

    private fun initView() {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        binding.rvCuacaPerjam.layoutManager = layoutManager
    }

    private fun showHourlyList(data : List<HourlyWeatherItem>) {
        with(binding) {
            cuacaPerjamAdapter = CuacaPerjamAdapter(data)
            rvCuacaPerjam.adapter= cuacaPerjamAdapter
        }
    }

    private fun filterWeeklyWeather() {
        with(binding) {
            ivFilter.setOnClickListener {
                findNavController().navigate(R.id.navigation_cuaca_perMinggu)
            }
        }
    }


}