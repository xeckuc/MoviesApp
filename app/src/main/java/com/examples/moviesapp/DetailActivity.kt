package com.examples.moviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
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
}
