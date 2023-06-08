package com.digilokal.dilo.remote.response

import com.google.gson.annotations.SerializedName

data class InfluencersResponse(

	@field:SerializedName("message")
	val message: List<MessagesItem>
)

data class MessagesItem(

	@field:SerializedName("user_email")
	val userEmail: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("user_name")
	val userName: String,

	@field:SerializedName("user_field_area")
	val userFieldArea: String,

	@field:SerializedName("user_city")
	val userCity: String
)
