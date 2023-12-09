package com.msib.growsmart.ui.lms.metan

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.msib.growsmart.databinding.FragmentMacamMediaTanamBinding
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.ui.factory.ViewModelFactory
import com.msib.growsmart.ui.lms.modul.LmsGuideAplikasiActivity

class LmsMediaTanamFragment : Fragment() {
    private var _binding: FragmentMacamMediaTanamBinding? = null
    private val binding get() = _binding!!
    private val lmsViewModel by viewModels<LmsMetanViewModel>{
        ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
    }
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
    private lateinit var token: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMacamMediaTanamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
    }

    private fun initObserver() {
        lmsViewModel.getUser().observe(viewLifecycleOwner) {
            if(it.isLogin) {
                token = it.token
                val id = 3
                lmsViewModel.getLmsGroup(token, id.toString())
                lmsViewModel.getLmsGroup.observe(viewLifecycleOwner) { lms ->
                    with(binding) {
                        tvBen.text = Html.fromHtml(lms.modul.benefit)
                    }
                }
            }
        }
        binding.btnMulai.setOnClickListener{
            LmsGuideAplikasiActivity.start(requireContext())
        }
    }

}