package com.digilokal.android.ui.main.profile.editprofile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.digilokal.android.R
import com.digilokal.android.data.repository.UserPreference
import com.digilokal.android.databinding.ActivityEditProfileBinding
import com.digilokal.android.di.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var editProfileViewModel: EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.supportActionBar?.setTitle(R.string.title_profile)

        setupViewModel()
        setupAction()

        editProfileViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        editProfileViewModel.isButtonEnabled.observe(this) { isEnabled ->
            binding.editButton.isEnabled = isEnabled
        }

        editProfileViewModel.editSuccess.observe(this) { success ->
            if (success) {

            }
        }
    }

    private fun setupViewModel() {
        editProfileViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), this)
        )[EditProfileViewModel::class.java]
    }

    private fun setupAction() {
        binding.editButton.setOnClickListener {
            val name = binding.edEditName.text.toString()
            val detail = binding.edEditDetail.text.toString()
            when {
                name.isEmpty() -> {
                    binding.nameEditTextLayout.error = "Enter your name"
                }
                detail.isEmpty() -> {
                    binding.detailEditTextLayout.error = "Enter your details"
                }
                else -> {
                    editProfileViewModel.disableButton()
                    editProfileViewModel.editUser(name, detail)

                    val intent = Intent()
                    setResult(Activity.RESULT_OK, intent)
                    finish()

                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.bgProgressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}