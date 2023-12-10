package com.msib.growsmart.ui.forum.notifikasi

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.msib.growsmart.databinding.FragmentNotifikasiBinding
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.FormattedForumsItem
import com.msib.growsmart.ui.factory.ViewModelFactory

class NotifikasiFragment : Fragment() {

    private var _binding: FragmentNotifikasiBinding? = null
    private val binding get() = _binding!!
    private lateinit var notifAdapter: NotifikasiAdapter
    private val notifikasiViewModel by viewModels<NotifikasiViewModel>{
        ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
    }
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotifikasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        initView()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        binding.rvNotif.layoutManager = layoutManager
    }

    private fun showNotif(data : List<FormattedForumsItem>) {
        with(binding) {
            notifAdapter = NotifikasiAdapter(data)
            rvNotif.adapter = notifAdapter
        }
    }


    private fun initObserver() {
        notifikasiViewModel.getUser().observe(viewLifecycleOwner) {
            if(it.isLogin) {
                token = it.token
                notifikasiViewModel.getNotification(token)
                notifikasiViewModel.getNotifikasi.observe(viewLifecycleOwner) { notif ->
                    showNotif(notif)
                }
            }
        }
    }

}