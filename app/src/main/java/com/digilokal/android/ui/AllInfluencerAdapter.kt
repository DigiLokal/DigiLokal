package com.digilokal.android.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digilokal.android.databinding.ItemInfluencerBinding
import com.digilokal.android.remote.response.InfluencerItem
import com.digilokal.android.ui.detail.InfluencerDetailActivity
import com.digilokal.android.ui.detail.InfluencerDetailViewModel

class AllInfluencerAdapter(private val influencer: List<InfluencerItem>) :
    RecyclerView.Adapter<AllInfluencerAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(var binding: ItemInfluencerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding =
            ItemInfluencerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        with(holder.binding) {
//            Glide.with(holder.itemView.context)
//                .load(stories[position].photoUrl)
//                .into(ivStory)
            influencerUsername.text = influencer[position].userName
            city.text = influencer[position].userCity
            fieldArea.text = influencer[position].userFieldArea
            ttFollower.text = influencer[position].numFollowersTiktok
            igFollower.text = influencer[position].numFollowersInstagram
            twFollower.text = influencer[position].numFollowersTwitter
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