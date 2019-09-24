package com.examples.moviesapp.custom

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.examples.moviesapp.R
import kotlinx.android.synthetic.main.filter_layout.view.*

class MovieFilter(context: Context) : LinearLayout(context) {

    private val YEAR = 0
    private val TYPE = 1
    private val PLOT = 2

    init {

        View.inflate(context, R.layout.filter_layout, this)
        bindUI()
    }

    private fun bindUI() {

        tvYear.setOnClickListener {

            showPickerDialog(tvYear, YEAR)
        }

        tvPlot.setOnClickListener {

            showPickerDialog(tvPlot, PLOT)
        }

        tvType.setOnClickListener {

            showPickerDialog(tvType, TYPE)
        }

        btnClear.setOnClickListener {

            tvYear.text = ""
            tvPlot.text = ""
            tvType.text = ""
        }
    }

    private fun showPickerDialog(sender: TextView, type: Int) {

        val dataSource = when(type) {

            YEAR -> (2019 downTo 1980).toList().map { it.toString() }.toList().toTypedArray()
            TYPE -> context.resources.getStringArray(R.array.type)
            PLOT -> context.resources.getStringArray(R.array.plot)

            else -> return
        }

        val arrayAdapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, dataSource)

        val dialog = AlertDialog.Builder(context)
            .setSingleChoiceItems(arrayAdapter, -1) { dialog, which ->

                sender.text = dataSource[which]
                dialog.dismiss()
            }
            .create()

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.listView.divider = context.getDrawable(android.R.drawable.divider_horizontal_dark)

        dialog.show()
    }

    public fun getYear(): String? {

        if (tvYear.text.toString().isEmpty())
            return null

        return tvYear.text.toString()
    }

    public fun getType(): String? {

        if (tvType.text.toString().isEmpty())
            return null

        return tvType.text.toString()
    }

    public fun getPlot(): String? {

        if (tvPlot.text.toString().isEmpty())
            return null

        return tvPlot.text.toString()
    }
}