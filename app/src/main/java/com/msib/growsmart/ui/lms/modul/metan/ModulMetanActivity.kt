package com.msib.growsmart.ui.lms.modul.metan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.msib.growsmart.databinding.ActivityLmsMetanBinding
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.ModulItem
import com.msib.growsmart.ui.factory.ViewModelFactory
import com.msib.growsmart.ui.lms.LmsViewModel
import com.msib.growsmart.ui.lms.modul.ModulAdapter
import com.msib.growsmart.ui.lms.modul.ModulViewModel
import com.msib.growsmart.ui.lms.modul.PlayModulVideo

class ModulMetanActivity : AppCompatActivity(), PlayModulVideo {
    private lateinit var binding: ActivityLmsMetanBinding
    private lateinit var modulAdapter: ModulAdapter
    private val modulViewModel by viewModels<ModulViewModel>{
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }
    private val lmsViewModel by viewModels<LmsViewModel>{
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
    private lateinit var token: String
    private val listItemModul = mutableListOf<ModulItem>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLmsMetanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        initView()
    }

    private fun initObserver() {
        modulViewModel.getUser().observe(this) {
            if(it.isLogin) {
                token = it.token
                val idModul = 3
                modulViewModel.getLmsModul(token, idModul.toString())
                modulViewModel.getLmsModul.observe(this) { modul ->
                    showAllModul(modul)
                }
                lmsViewModel.getLmsGroup(token, idModul.toString())
                lmsViewModel.getLmsGroup.observe(this) { group ->
                    binding.tvIsiDeskripsi.text = group.modul.description
                }
            }
        }
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvModul.layoutManager = layoutManager
        modulAdapter = ModulAdapter(listItemModul, this)
        binding.rvModul.adapter = modulAdapter
    }

    private fun initVideo(url: String) {
        val filter = url.replace("https://youtu.be", "https://www.youtube.com/embed")
        val video = "<iframe width=\"350\" height=\"300\" src=\"$filter\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
        with(binding) {
            playerView.loadData(video,"text/html", "utf-8")
            playerView.settings.javaScriptEnabled = true
            playerView.webChromeClient = WebChromeClient()
        }
    }

    private fun showAllModul(data: List<ModulItem>) {
        listItemModul.clear()
        listItemModul.addAll(data.reversed())
        modulAdapter.notifyDataSetChanged()
    }

    override fun onPlayModulVideo(modulResponse: ModulItem) {
        initVideo(modulResponse.video)
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ModulMetanActivity::class.java)
            context.startActivity(starter)
        }
    }
}