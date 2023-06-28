package com.digilokal.android.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digilokal.android.R
import com.digilokal.android.data.network.response.InfluencerItem
import com.digilokal.android.databinding.ItemInfluencerMiniBinding
import com.digilokal.android.helper.formatFollowersCount
import com.digilokal.android.ui.main.detail.InfluencerDetailActivity
import com.digilokal.android.ui.main.detail.InfluencerDetailViewModel

class InfluencerAdapter(private val influencer: List<InfluencerItem>) :
    RecyclerView.Adapter<InfluencerAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(var binding: ItemInfluencerMiniBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding =
            ItemInfluencerMiniBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        with(holder.binding) {
//            Glide.with(holder.itemView.context)
//                .load(stories[position].photoUrl)
//                .into(ivStory)

            when (influencer[position].userName) {
                "influencer_1" -> {
                    ivInfluencer.setImageResource(R.drawable.influencer_1)
                    influencerUsername.text = "tarisharf"
                }

                "influencer_2" -> {
                    ivInfluencer.setImageResource(R.drawable.influencer_2)
                    influencerUsername.text = "tifanny_jolie"
                }
                "influencer_3" -> {
                    ivInfluencer.setImageResource(R.drawable.influencer_3)
                    influencerUsername.text = "graciellavioletta"
                }

                "influencer_4" -> {
                    ivInfluencer.setImageResource(R.drawable.influencer_4)
                    influencerUsername.text = "edwarddwardx"
                }
                else -> {
                    influencerUsername.text = influencer[position].userName
                }
            }


//            influencerUsername.text = influencer[position].userName
            city.text = influencer[position].userCity
            fieldArea.text = influencer[position].userFieldArea
            ttFollower.text = formatFollowersCount(influencer[position].numFollowersTiktok)
            igFollower.text = formatFollowersCount(influencer[position].numFollowersInstagram)
            twFollower.text = formatFollowersCount(influencer[position].numFollowersTwitter)
        }

        with(holder.itemView) {
            setOnClickListener {
                val intent = Intent(context, InfluencerDetailActivity::class.java)
                intent.putExtra(InfluencerDetailActivity.INFLUENCER, influencer[position])
                InfluencerDetailViewModel.influencer_username = influencer[position].userName
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = influencer.size
}