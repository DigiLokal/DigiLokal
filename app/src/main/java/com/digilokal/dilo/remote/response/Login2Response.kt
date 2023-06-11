package com.digilokal.dilo.remote.response

import com.google.gson.annotations.SerializedName

data class Login2Response(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("token")
	val token: String? = null
)
