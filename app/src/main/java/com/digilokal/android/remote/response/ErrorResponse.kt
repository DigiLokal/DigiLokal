package com.digilokal.android.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("detail")
	val detail: List<DetailItem>
)

data class DetailItem(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("loc")
	val loc: List<String>,

	@field:SerializedName("type")
	val type: String
)
