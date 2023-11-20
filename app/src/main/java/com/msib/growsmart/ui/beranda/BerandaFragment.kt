package com.msib.growsmart.ui.beranda

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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

        initObserver()
    }

    private fun initObserver() {
        with(binding) {
            berandaViewModel.getUser().observe(viewLifecycleOwner) {
                if(it.isLogin) {
                    tvTani.text = "Hey, ${it.name}"
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