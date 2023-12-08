package com.msib.growsmart.ui.forum.komentar

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.msib.growsmart.databinding.ActivityKomentarBinding
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.GetAllForumResponseItem
import com.msib.growsmart.response.Komentars
import com.msib.growsmart.response.PostKomentarResponse
import com.msib.growsmart.ui.factory.ViewModelFactory
import com.squareup.picasso.Picasso

class KomentarActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityKomentarBinding
    private val komentarViewModel by viewModels<KomentarViewModel>{
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
    private lateinit var token: String
    private lateinit var idForum: String
    private lateinit var komentarAdapter: KomentarAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKomentarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val komentar = intent.getParcelableExtra<GetAllForumResponseItem>("komentar")

        if(komentar != null) {
            with(binding) {
                Picasso.get().load(komentar.image).into(ivGambar)
                tvUser.text = komentar.nama
                tvIsiKomentar.text = komentar.isi
                tvJumlahKomentar.text = "${komentar.jumlahKomentar} Komentar"
            }
        }

        initObserver()
        initView()

    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvKomentar.layoutManager = layoutManager
    }

    private fun initObserver(){
        komentarViewModel.getUser().observe(this) { it ->
            val komentar = intent.getParcelableExtra<GetAllForumResponseItem>("komentar")
            if(it.isLogin) {
                if (komentar != null) {
                    token = it.token
                    idForum = komentar.forumId.toString()
                    komentarViewModel.getKomentar(token, idForum)
                    komentarViewModel.getKomentar.observe(this) { list ->
                        showAllKomentar(list)
                    }

                    binding.btnKirim.setOnClickListener {
                        komentarViewModel.postKomentar(
                            token,
                            idForum,
                            binding.etIsiKomentar.text.toString(),
                        )
                        komentarViewModel.postKomentar.observe(this) {
                            postKomentar(it)
                        }
                        recreate()
                    }
                }
            }
        }


    }


    private fun initPostKomentar() {
        komentarViewModel.postKomentar(
            token,
            idForum,
            binding.etIsiKomentar.text.toString(),
        )
        komentarViewModel.postKomentar.observe(this) {
            postKomentar(it)
        }
    }


    private fun showAllKomentar(data: List<Komentars>) {
        with(binding) {
            komentarAdapter = KomentarAdapter(data)
            rvKomentar.adapter = komentarAdapter
        }
    }

    private fun postKomentar(data: PostKomentarResponse) {
        if(data.error == true) {
            if (binding.etIsiKomentar.text.toString().isEmpty()) {
                Toast.makeText(
                    this@KomentarActivity,
                    "Silahkan Masukkan Komentar Terlebih Dahulu!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        } else {
            Toast.makeText(
                this@KomentarActivity,
                "Berhasil Mengirimkan Komentar",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    @SuppressLint("CheckResult")
    private fun initListener() {
        with(binding) {
            btnKirim.setOnClickListener{
                if (binding.etIsiKomentar.text.toString().isEmpty()) {
                    Toast.makeText(
                        this@KomentarActivity,
                        "Silahkan Masukkan Komentar Terlebih Dahulu!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    initPostKomentar()
                }
            }
        }
    }


}