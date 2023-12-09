package com.msib.growsmart.ui.lms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.msib.growsmart.databinding.FragmentLmsCaraMenggunakanAplikasiBinding
import com.msib.growsmart.ui.lms.modul.LmsGuideAplikasiActivity


class LmsCaraMenggunakanAplikasiFragment : Fragment() {
    private var _binding: FragmentLmsCaraMenggunakanAplikasiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLmsCaraMenggunakanAplikasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
    }

    private fun initObserver() {
        binding.btnMulai.setOnClickListener{
            LmsGuideAplikasiActivity.start(requireContext())
        }
    }

}