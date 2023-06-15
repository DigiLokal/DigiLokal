package com.digilokal.android.data.network.response

import com.google.gson.annotations.SerializedName

data class OrderResponse(

	@field:SerializedName("message")
	val message: List<OrderItem>
)

data class OrderItem(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("ordered_by")
	val orderedBy: String,

	@field:SerializedName("detail")
	val detail: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("social_media")
	val socialMedia: String,

	@field:SerializedName("status")
	val status: String
)
