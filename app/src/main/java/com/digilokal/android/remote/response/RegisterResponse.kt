package com.digilokal.android.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("password_check")
	val passwordCheck: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("message")
	val message: String
)
