package com.digilokal.android.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digilokal.android.R
import com.digilokal.android.databinding.ItemServicesBinding
import com.digilokal.android.remote.response.ServicesItem

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

            //image
//            Glide.with(holder.itemView.context)
//                .load(stories[position].photoUrl)
//                .into(ivStory)

            when (services[position].platform) {
                "TikTok" -> ivPlatform.setImageResource(R.drawable.ic_tiktok)
                "Instagram" -> ivPlatform.setImageResource(R.drawable.ic_instagram)
                else -> ivPlatform.setImageResource(R.drawable.ic_tiktok)
            }
            tvServicesTitle.text = services[position].title
            username.text = services[position].influencerUsername
            category.text = services[position].category
            price.text = services[position].price.toString()

        }

        with(holder.itemView) {
            setOnClickListener {
            }
        }
    }

    override fun getItemCount(): Int = services.size
}