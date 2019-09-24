package com.examples.moviesapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.examples.moviesapp.controllers.MainActivityController
import com.examples.moviesapp.controllers.contracts.Contracts
import com.examples.moviesapp.custom.MovieFilter
import com.examples.moviesapp.data.entities.Movie
import com.examples.moviesapp.data.entities.SearchResponse
import com.examples.moviesapp.data.network.OmdbClient
import com.examples.moviesapp.data.network.OmdbService
import com.examples.moviesapp.recycler.MoviesAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
                                        //MVC
class MainActivity : AppCompatActivity()/*, Contracts.MainActivityContract*/ {

    private val client = OmdbClient.createClient()
    private lateinit var movieFilter: MovieFilter

    //region MVC
    //MVC - Architecture Intro
//    private lateinit var controller: Contracts.MainActivityControllerContract
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //region MVC
        //MVC - Architecture Intro
//        controller = MainActivityController(this)
        //endregion

        addFilter()
        bindUI()
    }


    //region MVC
    //MVC - Architecture Intro
//    override fun showProgress(isShowing: Boolean) {
//        isProgressShowing(isShowing)
//    }
//
//    override fun errorDialog(message: String) {
//        showErrorDialog(message)
//    }
//
//    override fun showMovies(list: List<Movie>) {
//        setupRecyclerView(list)
//    }
    //endregion

    private fun bindUI() {

        ibSearch.setOnClickListener {

            hideKeyboard()

            if (etSearch.text.toString().isEmpty()) {

                showErrorDialog("Please insert title!")
                return@setOnClickListener
            }

//            controller.findMovies(
//                etSearch.text.toString(),
//                movieFilter.getYear(),
//                movieFilter.getType(),
//                movieFilter.getPlot()
//            )

            searchMovie(etSearch.text.toString())
        }

        etSearch.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                ibSearch.callOnClick()
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }

        ibFilter.setOnClickListener {

            if (movieFilter.visibility == View.VISIBLE) {
                movieFilter.visibility = View.GONE
            }
            else {
                movieFilter.visibility = View.VISIBLE
            }
        }
    }

    private fun searchMovie(title: String) {

        isProgressShowing(true)

        val searchCall = client
            .findMoviesByTitle(
                title,
                movieFilter.getType(),
                movieFilter.getYear(),
                movieFilter.getPlot()
            )

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

    private fun addFilter() {

        movieFilter = MovieFilter(this)
        movieFilter.visibility = View.GONE

        linearLayout.addView(movieFilter)
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
