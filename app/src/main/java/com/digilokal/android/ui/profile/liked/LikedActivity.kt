package com.digilokal.android.ui.profile.liked

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.digilokal.android.R
import com.digilokal.android.data.UserPreference
import com.digilokal.android.databinding.ActivityEditProfileBinding
import com.digilokal.android.databinding.ActivityLikedBinding
import com.digilokal.android.databinding.ActivityLoginBinding
import com.digilokal.android.helper.ViewModelFactory
import com.digilokal.android.ui.AllInfluencerAdapter
import com.digilokal.android.ui.InfluencerAdapter
import com.digilokal.android.ui.custom.ItemDecoration

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LikedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLikedBinding
    private lateinit var likedViewModel: LikedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.supportActionBar?.setTitle(R.string.liked_influencer)

        setupViewModel()
        setupInfluencerRv()

        likedViewModel.influencer.observe(this) {
            binding.rvInfluencer.adapter = AllInfluencerAdapter(it)
        }

        likedViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setupViewModel() {
        likedViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), this)
        )[LikedViewModel::class.java]
    }

    private fun setupInfluencerRv() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvInfluencer.layoutManager = layoutManager
        val spacing = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val itemDecoration = ItemDecoration(spacing)
        binding.rvInfluencer.addItemDecoration(itemDecoration)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.bgProgressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}