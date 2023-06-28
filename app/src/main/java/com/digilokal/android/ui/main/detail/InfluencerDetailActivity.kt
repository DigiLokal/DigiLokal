package com.digilokal.android.ui.main.detail

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
import com.digilokal.android.data.network.response.InfluencerItem
import com.digilokal.android.data.network.response.ServicesItem
import com.digilokal.android.data.repository.UserPreference
import com.digilokal.android.databinding.ActivityInfluencerDetailBinding
import com.digilokal.android.di.ViewModelFactory
import com.digilokal.android.helper.formatFollowersCount
import com.digilokal.android.helper.setupFullScreenMode
import com.digilokal.android.ui.adapters.RateCardAdapter
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

        when (influencer.userName) {
            "influencer_1" -> {
                binding.ivInfluencer.setImageResource(R.drawable.influencer_1)
                binding.influencerUsername.text = "tarisharf"
            }

            "influencer_2" -> {
                binding.ivInfluencer.setImageResource(R.drawable.influencer_2)
                binding.influencerUsername.text = "tifanny_jolie"
            }
            "influencer_3" -> {
                binding.ivInfluencer.setImageResource(R.drawable.influencer_3)
                binding.influencerUsername.text = "graciellavioletta"
            }

            "influencer_4" -> {
                binding.ivInfluencer.setImageResource(R.drawable.influencer_4)
                binding.influencerUsername.text = "edwarddwardx"
            }
            else -> {
                binding.influencerUsername.text = influencer.userName
            }
        }

//        binding.influencerUsername.text = influencer.userName
        binding.ttFollower.text = formatFollowersCount(influencer.numFollowersTiktok)
        binding.igFollower.text = formatFollowersCount(influencer.numFollowersInstagram)
        binding.twFollower.text = formatFollowersCount(influencer.numFollowersTwitter)
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