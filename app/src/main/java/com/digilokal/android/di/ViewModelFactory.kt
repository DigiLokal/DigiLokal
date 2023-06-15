package com.digilokal.android.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.digilokal.android.data.repository.UserPreference
import com.digilokal.android.ui.authentication.login.LoginViewModel
import com.digilokal.android.ui.authentication.signup.SignupViewModel
import com.digilokal.android.ui.authentication.splash.SplashViewModel
import com.digilokal.android.ui.main.MainViewModel
import com.digilokal.android.ui.main.detail.InfluencerDetailViewModel
import com.digilokal.android.ui.main.home.HomeViewModel
import com.digilokal.android.ui.main.home.influencer.InfluencerViewModel
import com.digilokal.android.ui.main.profile.ProfileViewModel
import com.digilokal.android.ui.main.profile.editprofile.EditProfileViewModel
import com.digilokal.android.ui.main.profile.liked.LikedViewModel
import com.digilokal.android.ui.main.projects.ProjectsViewModel

class ViewModelFactory(private val pref: UserPreference, private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(pref) as T
            }
            modelClass.isAssignableFrom(InfluencerViewModel::class.java) -> {
                InfluencerViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ProjectsViewModel::class.java) -> {
                ProjectsViewModel(pref) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(pref) as T
            }
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> {
                EditProfileViewModel(pref, context) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LikedViewModel::class.java) -> {
                LikedViewModel(pref,context) as T
            }
            modelClass.isAssignableFrom(InfluencerDetailViewModel::class.java) -> {
                InfluencerDetailViewModel(pref,context) as T
            }
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(pref, context) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref, context) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}