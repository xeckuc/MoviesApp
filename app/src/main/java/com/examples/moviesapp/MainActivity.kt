package com.examples.moviesapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.examples.moviesapp.data.entities.Movie
import com.examples.moviesapp.data.entities.SearchResponse
import com.examples.moviesapp.data.network.OmdbClient
import com.examples.moviesapp.data.network.OmdbService
import com.examples.moviesapp.recycler.MoviesAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val client = OmdbClient.createClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindUI()
    }

    private fun bindUI() {

        ibSearch.setOnClickListener {

            hideKeyboard()

            if (etSearch.text.toString().isEmpty()) {

                showErrorDialog("Please insert title!")
                return@setOnClickListener
            }

            searchMovie(etSearch.text.toString())
        }

        etSearch.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                ibSearch.callOnClick()
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
    }

    private fun searchMovie(title: String) {

        isProgressShowing(true)

        val searchCall = client.findMoviesByTitle(title)

        searchCall.enqueue(object: Callback<SearchResponse> {

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                isProgressShowing(false)
                showErrorDialog("Retrofit Error!!")
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {

                if (response.code() == 200) {

                    val searchResult = response.body()

                    searchResult?.let {

                        if (it.response) {

                            setupRecyclerView(it.search)
                        }
                        else {

                            showErrorDialog(it.error)
                        }
                    }
                }

                isProgressShowing(false)
            }
        })
    }

    private fun setupRecyclerView(list: List<Movie>) {

        val adapter = MoviesAdapter(this, list)

        rvMovies.layoutManager = LinearLayoutManager(this)
        rvMovies.adapter = adapter
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

    private fun isProgressShowing(isShowing: Boolean) {

        if (isShowing) progressLayout.visibility = View.VISIBLE else progressLayout.visibility = View.GONE
    }

    private fun hideKeyboard() {

        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        val view = currentFocus

        view?.let {

            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}
