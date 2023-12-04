package com.msib.growsmart.ui.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.msib.growsmart.databinding.FragmentForumBinding
import com.msib.growsmart.response.GetAllForumResponseItem

class ForumFragment : Fragment() {
    private var _binding: FragmentForumBinding? = null
    private val binding get() = _binding!!
    private lateinit var forumAdapter: ForumAdapter
    private val forumViewModel by viewModels<ForumViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        initView()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        binding.rvAllForum.layoutManager = layoutManager
    }

    private fun initObserver() {
        forumViewModel.getAllForum()
        forumViewModel.getAllForum.observe(viewLifecycleOwner) { list ->
            showAllForum(list)
        }
        forumViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }

    private fun showAllForum(data: List<GetAllForumResponseItem>) {
        with(binding) {
            forumAdapter = ForumAdapter(data)
            rvAllForum.adapter= forumAdapter
        }
    }



}