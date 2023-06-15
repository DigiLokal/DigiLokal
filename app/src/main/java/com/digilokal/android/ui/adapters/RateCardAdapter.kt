package com.digilokal.android.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digilokal.android.R
import com.digilokal.android.data.network.response.ServicesItem
import com.digilokal.android.databinding.ItemRateCardBinding
import com.digilokal.android.helper.formatPrice
import com.digilokal.android.ui.main.detail.InfluencerDetailViewModel

class RateCardAdapter(private val services: List<ServicesItem>, private val listener: OnRateCardItemClickListener) :
    RecyclerView.Adapter<RateCardAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(var binding: ItemRateCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding =
            ItemRateCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        with(holder.binding) {

            //image
//            Glide.with(holder.itemView.context)
//                .load(stories[position].photoUrl)
//                .into(ivStory)

            when (services[position].platform) {
                "TikTok" -> {
                    rateCardPlatform.setImageResource(R.drawable.tiktok_text)
                    ivRateCard.setImageResource(R.drawable.ph_service)
                }

                "Instagram" -> {
                    rateCardPlatform.setImageResource(R.drawable.instagram_text)
                    ivRateCard.setImageResource(R.drawable.ph_services_2)
                }
            }
            rateCardTitle.text = services[position].title
            rateCardPrice.text = formatPrice(services[position].price)
            servicesDescription.text = services[position].description

        }

        with(holder.itemView) {
            setOnClickListener {
                InfluencerDetailViewModel.service_id = services[position].serviceId
                listener.onRateCardItemClick(services[position])
            }
        }
    }
    interface OnRateCardItemClickListener {
        fun onRateCardItemClick(service: ServicesItem)
    }

    override fun getItemCount(): Int = services.size
}