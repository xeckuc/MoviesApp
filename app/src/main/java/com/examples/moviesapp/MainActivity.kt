package com.examples.moviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.examples.moviesapp.data.entities.SearchResponse
import com.examples.moviesapp.data.network.OmdbClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = OmdbClient.createClient()

        val searchCall = client.findMoviesByTitle("Spiderman")

        searchCall.enqueue(object: Callback<SearchResponse> {

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                tvTest.text = getString(R.string.error_retrofit_failure)
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {

                if (response.code() == 200) {

                    val searchResult = response.body()

                    searchResult?.let {

                        if (it.response) {

                            tvTest.text = it.search.toString()
                        }
                        else {
                            tvTest.text = it.error
                        }
                    }
                }
            }


        })
    }
}
