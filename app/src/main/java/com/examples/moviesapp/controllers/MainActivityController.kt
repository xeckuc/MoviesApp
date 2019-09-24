package com.examples.moviesapp.controllers

import com.examples.moviesapp.controllers.contracts.Contracts
import com.examples.moviesapp.data.entities.SearchResponse
import com.examples.moviesapp.data.network.OmdbClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityController(private val activity: Contracts.MainActivityContract): Contracts.MainActivityControllerContract {

    override fun findMovies(title: String, year: String?, type: String?, plot: String?) {

        activity.showProgress(true)

        val client = OmdbClient.createClient()

        val searchCall = client
            .findMoviesByTitle(
                title,
                type,
                year,
                plot
            )

        searchCall.enqueue(object: Callback<SearchResponse> {

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                activity.showProgress(true)
                activity.errorDialog("Retrofit Error")
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {

                if (response.code() == 200) {

                    val searchResult = response.body()

                    searchResult?.let {

                        if (it.response) {
                            activity.showMovies(it.search)
                        }
                        else {
                            activity.errorDialog(it.error)
                        }
                    }
                }

                activity.showProgress(false)
            }
        })
    }
}