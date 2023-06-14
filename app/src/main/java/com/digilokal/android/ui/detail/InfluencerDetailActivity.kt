package com.digilokal.android.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.digilokal.android.R
import com.digilokal.android.data.UserPreference
import com.digilokal.android.databinding.ActivityInfluencerDetailBinding
import com.digilokal.android.helper.ViewModelFactory
import com.digilokal.android.helper.setupFullScreenMode
import com.digilokal.android.remote.response.InfluencerItem
import com.digilokal.android.remote.response.ServicesItem
import com.digilokal.android.ui.RateCardAdapter
import com.digilokal.android.ui.custom.ItemDecoration

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class InfluencerDetailActivity : AppCompatActivity(), RateCardAdapter.OnRateCardItemClickListener {

    private lateinit var influencerDetailViewModel: InfluencerDetailViewModel
    private lateinit var binding: ActivityInfluencerDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfluencerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFullScreenMode(window, supportActionBar)
        setupViewModel()
        setupRateCardRv()

        showData()

        influencerDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }


    private fun showData() {
        val influencer = intent.getParcelableExtra<InfluencerItem>(INFLUENCER) as InfluencerItem

        // TODO for image
//        Glide.with(applicationContext)
//            .load(story.photoUrl)
//            .into(binding.ivDetailStory)
        binding.influencerUsername.text = influencer.userName
        binding.ttFollower.text = influencer.numFollowersTiktok
        binding.igFollower.text = influencer.numFollowersInstagram
        binding.twFollower.text = influencer.numFollowersTwitter
        binding.city.text = influencer.userCity

        influencerDetailViewModel.rateCardAvailable.observe(this) { isRateCardAvailable ->
            binding.rateCardLayout.visibility = if (isRateCardAvailable) View.VISIBLE else View.GONE
        }
        influencerDetailViewModel.services.observe(this) { services ->
            binding.rvRateCard.adapter = RateCardAdapter(services,this)
        }

        binding.fabLike.setOnClickListener{
            influencerDetailViewModel.postLike(influencer.userName)
        }

    }

    private fun setupViewModel() {
        influencerDetailViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), this)
        )[InfluencerDetailViewModel::class.java]

    }

    private fun setupRateCardRv() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvRateCard.layoutManager = layoutManager
        val spacing = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val itemDecoration = ItemDecoration(spacing)
        binding.rvRateCard.addItemDecoration(itemDecoration)
    }

    override fun onRateCardItemClick(service: ServicesItem) {

        influencerDetailViewModel.postOrder()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.bgProgressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val INFLUENCER = "influencer"
    }
}