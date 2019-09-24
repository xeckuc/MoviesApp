package com.examples.moviesapp.data.network

import com.examples.moviesapp.data.entities.Movie
import com.examples.moviesapp.data.entities.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbService {

    @GET(".")
    fun findMoviesByTitle(
        @Query("s") title: String,
        @Query("type") type: String?,
        @Query("y") year: String?,
        @Query("plot") plot: String?
    ) : Call<SearchResponse>

    @GET(".")
    fun getMovieInfo(
        @Query("i") imdbID: String
    ) : Call<Movie>
}