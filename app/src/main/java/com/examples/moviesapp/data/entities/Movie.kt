package com.examples.moviesapp.data.entities

import com.google.gson.annotations.SerializedName

data class Movie (

    //Short
    @SerializedName("Title")
    val title: String,

    @SerializedName("Year")
    val year: String,

    @SerializedName("Type")
    val type: String,

    @SerializedName("Poster")
    val posterUrl: String,

    val imdbID: String,

    //Full
    @SerializedName("Rated")
    val rated: String,

    @SerializedName("Released")

    val released: String,

    @SerializedName("Genre")
    val genre: String,

    @SerializedName("Actors")
    val actors: String,

    @SerializedName("director")
    val director: String,

    @SerializedName("Plot")
    val plot: String,

    val imdbRating: String,

    val imdbVotes: String
)