package com.digilokal.android.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.digilokal.android.data.UserPreference
import com.digilokal.android.ui.detail.InfluencerDetailViewModel
import com.digilokal.android.ui.home.HomeViewModel
import com.digilokal.android.ui.influencer.InfluencerViewModel
import com.digilokal.android.ui.login.LoginViewModel
import com.digilokal.android.ui.main.MainViewModel
import com.digilokal.android.ui.profile.ProfileViewModel
import com.digilokal.android.ui.profile.editprofile.EditProfileViewModel
import com.digilokal.android.ui.profile.liked.LikedViewModel
import com.digilokal.android.ui.projects.ProjectsViewModel
import com.digilokal.android.ui.signup.SignupViewModel
import com.digilokal.android.ui.splash.SplashViewModel

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