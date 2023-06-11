package com.digilokal.dilo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digilokal.dilo.R
import com.digilokal.dilo.databinding.ItemServicesBinding
import com.digilokal.dilo.remote.response.ServicesItem

class ServicesAdapter(private val services: List<ServicesItem>) :
    RecyclerView.Adapter<ServicesAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(var binding: ItemServicesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding =
            ItemServicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        with(holder.binding) {

//            Glide.with(holder.itemView.context)
//                .load(stories[position].photoUrl)
//                .into(ivStory)

            when (services[position].platform) {
                "TikTok" -> ivPlatform.setImageResource(R.drawable.ic_tiktok)
                "Instagram" -> ivPlatform.setImageResource(R.drawable.ic_instagram)
                else -> ivPlatform.setImageResource(R.drawable.ic_tiktok) // Default icon if platform value is unknown
            }
            tvServicesTitle.text = services[position].title
            username.text = services[position].influencerUsername
            category.text = services[position].category
            price.text = services[position].price.toString()

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

    override fun getItemCount(): Int = services.size
}