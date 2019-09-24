package com.examples.moviesapp.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.examples.moviesapp.R
import com.examples.moviesapp.data.entities.Movie

class MoviesAdapter(
    private val context: Context,
    private val list: List<Movie>
) : RecyclerView.Adapter<MoviesAdapter.MovieHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {

        return MovieHolder(LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {

        holder.tvTitle.text = list[position].title
        holder.tvYear.text = list[position].year
        holder.tvType.text = list[position].type

        holder.itemView.setOnClickListener {

            //TODO
        }
    }


    inner class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvYear = itemView.findViewById<TextView>(R.id.tvYear)
        val tvType = itemView.findViewById<TextView>(R.id.tvType)
    }

}