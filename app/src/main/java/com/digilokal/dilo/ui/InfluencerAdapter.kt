package com.digilokal.dilo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digilokal.dilo.databinding.ItemInfluencerBinding
import com.digilokal.dilo.remote.response.InfluencerItem

class InfluencerAdapter(private val influencer: List<InfluencerItem>) :
    RecyclerView.Adapter<InfluencerAdapter.StoryViewHolder>() {

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
//                val optionsCompat: ActivityOptionsCompat =
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        context as Activity,
//                        Pair(holder.binding.ivStory, "photo"),
//                        Pair(holder.binding.tvName, "name"),
//                        Pair(holder.binding.tvDescription, "description"),
//                    )

//                val intent = Intent(context, DetailActivity::class.java)
//                intent.putExtra(DetailActivity.STORY, stories[position])
//                context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    override fun getItemCount(): Int = influencer.size
}