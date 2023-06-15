package com.digilokal.android.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class InfluencersResponse(

	@field:SerializedName("influencers")
	val influencer: List<InfluencerItem>
)

@Parcelize
data class InfluencerItem(

	@field:SerializedName("user_email")
	val userEmail: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("user_name")
	val userName: String,

	@field:SerializedName("user_field_area")
	val userFieldArea: String,

	@field:SerializedName("user_city")
	val userCity: String,

	@field:SerializedName("num_followers_instagram")
	val numFollowersInstagram: String,

	@field:SerializedName("num_followers_twitter")
	val numFollowersTwitter: String,

	@field:SerializedName("num_followers_tiktok")
	val numFollowersTiktok: String
)  : Parcelable
