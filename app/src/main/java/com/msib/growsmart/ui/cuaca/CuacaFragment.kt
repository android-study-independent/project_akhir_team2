package com.msib.growsmart.ui.cuaca

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.location.LocationServices
import com.msib.growsmart.databinding.FragmentCuacaBinding
import com.msib.growsmart.response.HourlyWeatherItem
import com.squareup.picasso.Picasso


class CuacaFragment : Fragment() {

    private var _binding: FragmentCuacaBinding? = null
    private val binding get() = _binding!!
    private lateinit var cuacaAdapter: CuacaAdapter
    private val cuacaViewModel by viewModels<CuacaViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCuacaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        mFused()
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
                cuacaViewModel.getListHourlyWeather(location.longitude, location.latitude)
                cuacaViewModel.getHourlyWeather.observe(viewLifecycleOwner) { list ->
                    showHourlyList(list)
                }
                cuacaViewModel.getCurrentWeather(location.longitude, location.latitude)
                cuacaViewModel.getCurrentWeather.observe(viewLifecycleOwner) { data ->
                    with(binding) {
                        Picasso.get().load(data.currentWeather.weatherIcon).into(ivWeather)

                        tvKota.text = data.currentWeather.city
                        tvSuhu.text = " ${data.currentWeather.temperature.toInt()}℃ "
                        tvKelembapan.text = "Kelembapan ${data.currentWeather.humidity}%"
                        tvKegiatanKet.text = data.currentWeather.suggest
                        tvInfoPeluangHujan.text = data.currentWeather.rainChance
                        tvInfoKelembapan.text = "${data.currentWeather.humidity}%"
                        tvInfoIndexUV.text = data.currentWeather.indexUV.toString()
                    }
                }

            }

            Log.d(
                "My Current location",
                "Lat : ${location?.latitude} Long : ${location?.longitude}"
            )

        }
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
        binding.rvHourlyWeather.layoutManager = layoutManager
    }

    private fun showHourlyList(data : List<HourlyWeatherItem>) {
        with(binding) {
            cuacaAdapter = CuacaAdapter(requireContext(), data)
            rvHourlyWeather.adapter= cuacaAdapter
        }
    }

}