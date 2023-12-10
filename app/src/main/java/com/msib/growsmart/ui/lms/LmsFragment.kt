package com.msib.growsmart.ui.lms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.msib.growsmart.R
import com.msib.growsmart.databinding.FragmentLmsBinding

class LmsFragment : Fragment() {

    private var _binding: FragmentLmsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNavigate()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initNavigate() {
        with(binding) {
            cvGuide.setOnClickListener {
                findNavController().navigate(R.id.navigation_lms_guide)
            }
            cvMenanam.setOnClickListener {
                findNavController().navigate(R.id.navigation_lms_menanam)
            }
            cvMetan.setOnClickListener {
                findNavController().navigate(R.id.navigation_lms_metan)
            }
        }
    }

}