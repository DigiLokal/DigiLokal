package com.digilokal.android.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digilokal.android.R
import com.digilokal.android.data.network.response.ServicesItem
import com.digilokal.android.databinding.ItemServicesBinding
import com.digilokal.android.helper.formatPrice

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
            price.text = formatPrice(services[position].price)

        }

        with(holder.itemView) {
            setOnClickListener {
            }
        }
    }

    override fun getItemCount(): Int = services.size
}