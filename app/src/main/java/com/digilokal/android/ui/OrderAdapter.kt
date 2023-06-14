package com.digilokal.android.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digilokal.android.R
import com.digilokal.android.databinding.ItemOrderBinding
import com.digilokal.android.databinding.ItemServicesBinding
import com.digilokal.android.remote.response.OrderItem
import com.digilokal.android.remote.response.ServicesItem

class OrderAdapter(private val order: List<OrderItem>) :
    RecyclerView.Adapter<OrderAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(var binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding =
            ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        with(holder.binding) {

            //image
//            Glide.with(holder.itemView.context)
//                .load(stories[position].photoUrl)
//                .into(ivStory)

            when (order[position].socialMedia) {
                "TikTok" -> rateCardPlatform.setImageResource(R.drawable.tiktok_text)
                "Instagram" -> rateCardPlatform.setImageResource(R.drawable.instagram_text)
                else -> rateCardPlatform.setImageResource(R.drawable.tiktok_text)
            }
            rateCardTitle.text = order[position].nama
            servicesDescription.text = order[position].detail
            rateCardPrice.text = order[position].harga.toString()
            tvOrderStatus.text = order[position].status

        }

        with(holder.itemView) {
            setOnClickListener {

            }
        }
    }

    override fun getItemCount(): Int = order.size
}