package com.digilokal.dilo.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("")
	val token: String,

	@field:SerializedName("message")
	val message: String
)
