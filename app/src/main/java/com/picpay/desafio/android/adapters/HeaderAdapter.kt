package com.picpay.desafio.android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.picpay.desafio.android.MainActivity
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R

/**
 * Adapter usado no Header para o [RecyclerView] na [MainActivity].
 */

class HeaderAdapter: RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_header, parent, false)
        return HeaderViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {}

    override fun getItemCount(): Int {
        return 1
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)
}