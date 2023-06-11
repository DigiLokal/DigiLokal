package com.digilokal.dilo.remote.response

import com.google.gson.annotations.SerializedName

data class AllServicesResponse(

	@field:SerializedName("services")
	val services: List<ServicesItem>
)

data class ServicesItem(

	@field:SerializedName("influencer_id")
	val influencerId: String,

	@field:SerializedName("influencer_username")
	val influencerUsername: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("service_id")
	val serviceId: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("platform")
	val platform: String
)
