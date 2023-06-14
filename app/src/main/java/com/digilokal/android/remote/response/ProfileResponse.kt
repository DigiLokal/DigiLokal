package com.digilokal.android.remote.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("message")
	val message: List<MessageItem>
)

data class MessageItem(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("detail")
	val detail: String? = null,

	@field:SerializedName("tipe")
	val tipe: String,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
