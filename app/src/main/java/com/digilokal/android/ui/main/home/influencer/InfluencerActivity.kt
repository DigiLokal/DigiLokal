package com.digilokal.android.ui.main.home.influencer

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.digilokal.android.R
import com.digilokal.android.data.repository.UserPreference
import com.digilokal.android.databinding.ActivityInfluencerBinding
import com.digilokal.android.di.ViewModelFactory
import com.digilokal.android.helper.setupFullScreenMode
import com.digilokal.android.ui.adapters.AllInfluencerAdapter
import com.digilokal.android.ui.custom.ItemDecoration


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class InfluencerActivity : AppCompatActivity() {


    private lateinit var binding: ActivityInfluencerBinding
    private lateinit var influencerViewModel: InfluencerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfluencerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFullScreenMode(window, supportActionBar)
        setupViewModel()
        setupInfluencerRv()

        influencerViewModel.influencer.observe(this) {
            binding.rvInfluencer.adapter = AllInfluencerAdapter(it)
        }

        influencerViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setupViewModel() {
        influencerViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), this)
        )[InfluencerViewModel::class.java]
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