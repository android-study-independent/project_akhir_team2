package com.msib.growsmart.ui.lms.modul.guide

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
import com.msib.growsmart.databinding.ActivityLmsGuideAplikasiBinding
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.ModulItem
import com.msib.growsmart.ui.factory.ViewModelFactory
import com.msib.growsmart.ui.lms.modul.ModulAdapter
import com.msib.growsmart.ui.lms.modul.ModulViewModel
import com.msib.growsmart.ui.lms.modul.PlayModulVideo

class ModulGuideAplikasiActivity : AppCompatActivity(), PlayModulVideo {
    private lateinit var binding: ActivityLmsGuideAplikasiBinding
    private lateinit var modulAdapter: ModulAdapter
    private val modulViewModel by viewModels<ModulViewModel>{
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
    private lateinit var token: String
    private val listItemModul = mutableListOf<ModulItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLmsGuideAplikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        initView()
    }

    private fun initObserver() {
        modulViewModel.getUser().observe(this) {
            if(it.isLogin) {
                token = it.token
                val idModul = 1
                modulViewModel.getLmsModul(token, idModul.toString())
                modulViewModel.getLmsModul.observe(this) { modul ->
                    showAllModul(modul)
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
        val rep = url.replace("https://youtu.be", "https://www.youtube.com/embed")
        val video = "<iframe width=\"350\" height=\"300\" src=\"$rep\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
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

    private fun initVideo() {
        val videoUrl  = "https://youtu.be/Q3tSTO5doJ4?si=d41jTBhJpIHgMHjW"
        val rep = videoUrl.replace("https://youtu.be", "https://www.youtube.com/embed")
        val video = "<iframe width=\"350\" height=\"300\" src=\"$rep\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
        with(binding) {
            playerView.loadData(video,"text/html", "utf-8")
            playerView.settings.javaScriptEnabled = true
            playerView.webChromeClient = WebChromeClient()
        }
    }


    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ModulGuideAplikasiActivity::class.java)
            context.startActivity(starter)
        }
    }

}