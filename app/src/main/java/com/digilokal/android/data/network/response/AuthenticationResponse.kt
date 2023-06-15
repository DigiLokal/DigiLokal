package com.digilokal.android.data.network.response

import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("token")
	val token: String? = null
)
