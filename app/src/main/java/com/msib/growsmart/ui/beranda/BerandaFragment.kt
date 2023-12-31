package com.msib.growsmart.ui.beranda

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.msib.growsmart.databinding.FragmentBerandaBinding
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.ArticlesItem
import com.msib.growsmart.ui.artikel.ArtikelActivity
import com.msib.growsmart.ui.artikel_terbaru.HalamanArticleTerbaruActivity
import com.msib.growsmart.ui.factory.ViewModelFactory
import com.msib.growsmart.ui.login.LoginActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date


class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!
    private lateinit var berandaArticleAdapter: BerandaArticleAdapter
    private val berandaViewModel by viewModels<BerandaViewModel> {
        ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
    }
    private lateinit var preference: UserPreference
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
    private lateinit var mFusedClient: FusedLocationProviderClient
    private lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mFusedClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        getCurrentLocation()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun getCurrentLocation() {
        if (isLocationEnabled()) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions()
                return
            }
            val tokenSource = CancellationTokenSource()
            val token = tokenSource.token
            mFusedClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, token)
                .addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    Log.d("GPS Beranda", "${location!!.latitude} and ${location.longitude}")
                }
        } else {
            Toast.makeText(requireContext(), "Please turn on location", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            BerandaActivity.PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == BerandaActivity.PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getCurrentLocation()
            }
        }

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
                with(binding) {
                    berandaViewModel.getWeather(token, location.longitude, location.latitude)
                    berandaViewModel.getWeather.observe(viewLifecycleOwner) { data ->

                        val date = Date ()
                        val dayOfWeek = DateFormat.format ("dd/MM/yyyy", date)
                        val month = DateFormat.format ("MMM", date)
                        val c = Calendar.getInstance()
                        val dayNum = c.get(Calendar.DAY_OF_MONTH)
                        val year = c.get(Calendar.YEAR)

                        val cal = Calendar.getInstance()
                        if (dayOfWeek != null) {
                            cal.time = Date ()
                        }
                        val dayName = when (cal.get(Calendar.DAY_OF_WEEK)) {
                            1 -> "Minggu"
                            2 -> "Senin"
                            3 -> "Selasa"
                            4 -> "Rabu"
                            5 -> "Kamis"
                            6 -> "Jumat"
                            7 -> "Sabtu"
                            else -> "Hari Tidak Valid"
                        }


                        tvTanggal.text = "$dayName, $dayNum $month $year"
                        tvKota.text = data.currentWeather.city
                        tvWeather.text = data.currentWeather.weatherDescription
                        tvSuhu.text = " ${data.currentWeather.temperature.toInt()}℃ "
                        tvKelembapan.text = "Kelembapan ${data.currentWeather.humidity}%"
                        Picasso.get().load(data.currentWeather.weatherIcon).into(ivWeather)

                    }
                    berandaViewModel.isLoading.observe(viewLifecycleOwner) {
                        showLoading(it)
                    }
                }
            }

            Log.d(
                "My Current location",
                "Lat : ${location?.latitude} Long : ${location?.longitude}"
            )

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preference = UserPreference.getInstance(requireContext().dataStore)

        initObserver()
        initView()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        binding.rvArticle.layoutManager = layoutManager
    }

    private fun showLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }

    private fun showAllArticle(data : List<ArticlesItem>) {
        with(binding) {
            berandaArticleAdapter = BerandaArticleAdapter(data)
            rvArticle.adapter = berandaArticleAdapter
        }
    }

    private fun initObserver() {
        with(binding) {
            berandaViewModel.getUser().observe(viewLifecycleOwner) {
                if(it.isLogin) {
                    tvCuaca.text = "Hey, ${it.name}"
                    token = it.token
                    mFused()
                }
            }

            artikel.setOnClickListener {
                val intent = Intent(requireContext(), HalamanArticleTerbaruActivity::class.java)
                startActivity(intent)
            }
            lihatsemua.setOnClickListener {
                val intent = Intent(requireContext(), ArtikelActivity::class.java)
                startActivity(intent)
            }


            ivProfile.setOnClickListener {
                lifecycleScope.launch {
                    preference.logout()
                }
                LoginActivity.start(requireContext())
            }
        }
        berandaViewModel.getAllArticle()
        berandaViewModel.getArticle.observe(viewLifecycleOwner) { list ->
            showAllArticle(list)
        }
    }

}