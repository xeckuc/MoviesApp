package com.examples.moviesapp

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.examples.moviesapp.data.entities.Movie
import com.examples.moviesapp.data.network.OmdbClient
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imdbID = intent.getStringExtra("imdbID")

        if (!imdbID.isNullOrEmpty()) {

            getMovieInfo(imdbID)
        }
    }

    private fun showErrorDialog(message: String) {

        AlertDialog.Builder(this)
            .setTitle("Uh Oh!")
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->

                dialog.dismiss()
            }
            .create().show()
    }

    private fun getMovieInfo(imdbID: String) {

        isProgressShowing(true)

        val client = OmdbClient.createClient()

        val movieInfoCall = client.getMovieInfo(imdbID)

        movieInfoCall.enqueue(object: Callback<Movie> {

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                isProgressShowing(false)
                showErrorDialog("Retrofit Error!!")
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {

                if (response.code() == 200) {

                    val movieResult = response.body()

                    movieResult?.let {

                        fillMovieInfo(it)
                    }
                }
            }


        })
    }

    private fun fillMovieInfo(movie: Movie) {

        tvTitle.text = movie.title
        tvRelease.text = movie.released
        tvGenre.text = movie.genre
        tvPlot.text = movie.plot
        tvRating.text = movie.imdbRating

        Glide.with(this).load(movie.posterUrl)
            .listener(object: RequestListener<Drawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {

                    imageView.setImageDrawable(getDrawable(R.drawable.ic_launcher_background))
                    isProgressShowing(false)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {

                    isProgressShowing(false)
                    return false
                }
            }).into(imageView)
    }

    private fun isProgressShowing(isShowing: Boolean) {

        if (isShowing) progressLayout.visibility = View.VISIBLE else progressLayout.visibility = View.GONE
    }
}
