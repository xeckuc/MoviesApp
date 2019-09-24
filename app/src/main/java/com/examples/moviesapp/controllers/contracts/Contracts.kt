package com.examples.moviesapp.controllers.contracts

import com.examples.moviesapp.data.entities.Movie

interface Contracts {

    interface MainActivityContract {

        fun showProgress(isShowing: Boolean)
        fun errorDialog(message: String)
        fun showMovies(list: List<Movie>)
    }

    interface MainActivityControllerContract {

        fun findMovies(title: String, year: String?, type: String?, plot: String?)
    }
}