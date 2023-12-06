package com.msib.growsmart.ui.forum

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.msib.growsmart.databinding.FragmentForumBinding
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.GetAllForumResponseItem
import com.msib.growsmart.ui.factory.ViewModelFactory
import com.msib.growsmart.ui.forum.posting.PostingActivity

class ForumFragment : Fragment() {
    private var _binding: FragmentForumBinding? = null
    private val binding get() = _binding!!
    private lateinit var forumAdapter: ForumAdapter
    private val forumViewModel by viewModels<ForumViewModel>{
        ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
    }
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
    private lateinit var token: String
    private val listItemForum = mutableListOf<GetAllForumResponseItem>()

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
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvAllForum.layoutManager = layoutManager
        forumAdapter = ForumAdapter(listItemForum)
        binding.rvAllForum.adapter = forumAdapter
    }

    private fun initObserver(){
        forumViewModel.getUser().observe(viewLifecycleOwner) {
            if(it.isLogin) {
                token = it.token
                binding.tvUser.text = "Hey, ${it.name}"
                initForum()
            }
        }
    }

    private fun initForum() {
        forumViewModel.getAllForum(token)
        forumViewModel.getAllForum.observe(viewLifecycleOwner) { list ->
            showAllForum(list)
        }
        forumViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        binding.layoutPostForum.setOnClickListener{
            PostingActivity.start(requireContext())
        }
    }

    private fun showLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }

    private fun showAllForum(data: List<GetAllForumResponseItem>) {
        listItemForum.clear()
        listItemForum.addAll(data.reversed())
        forumAdapter.notifyDataSetChanged()
    }



}