package com.examples.moviesapp.data.entities

import com.google.gson.annotations.SerializedName

data class SearchResponse (

    @SerializedName("Search")
    val search: List<Movie>,

    @SerializedName("TotalResults")
    val totalResults: String,

    @SerializedName("Response")
    val response: Boolean,

    @SerializedName("Error")
    val error: String
)