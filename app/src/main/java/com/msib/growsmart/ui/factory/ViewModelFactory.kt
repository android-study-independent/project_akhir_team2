package com.msib.growsmart.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.ui.beranda.BerandaViewModel
import com.msib.growsmart.ui.cuaca.CuacaViewModel
import com.msib.growsmart.ui.cuaca.perjam.CuacaPerjamViewModel
import com.msib.growsmart.ui.cuaca.perminggu.CuacaPermingguViewModel
import com.msib.growsmart.ui.forum.ForumViewModel
import com.msib.growsmart.ui.forum.komentar.KomentarViewModel
import com.msib.growsmart.ui.forum.posting.PostingViewModel
import com.msib.growsmart.ui.lms.guide.LmsGuideViewModel
import com.msib.growsmart.ui.lms.menanam.LmsMenanamViewModel
import com.msib.growsmart.ui.lms.metan.LmsMetanViewModel
import com.msib.growsmart.ui.login.LoginViewModel
import com.msib.growsmart.ui.splash.SplashViewModel

class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(pref) as T
            }
            modelClass.isAssignableFrom(BerandaViewModel::class.java) -> {
                BerandaViewModel(pref) as T
            }
            modelClass.isAssignableFrom(CuacaViewModel::class.java) -> {
                CuacaViewModel(pref) as T
            }
            modelClass.isAssignableFrom(CuacaPerjamViewModel::class.java) -> {
                CuacaPerjamViewModel(pref) as T
            }
            modelClass.isAssignableFrom(CuacaPermingguViewModel::class.java) -> {
                CuacaPermingguViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ForumViewModel::class.java) -> {
                ForumViewModel(pref) as T
            }
            modelClass.isAssignableFrom(PostingViewModel::class.java) -> {
                PostingViewModel(pref) as T
            }
            modelClass.isAssignableFrom(KomentarViewModel::class.java) -> {
                KomentarViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LmsGuideViewModel::class.java) -> {
                LmsGuideViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LmsMenanamViewModel::class.java) -> {
                LmsMenanamViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LmsMetanViewModel::class.java) -> {
                LmsMetanViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class : ${modelClass.name}")
        }
    }

}