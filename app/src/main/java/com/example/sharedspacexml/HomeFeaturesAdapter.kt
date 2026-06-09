package com.example.sharedspacexml

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView

class HomeFeaturesAdapter(
    private val context: Context,
    private val features: List<HomeFeature>,
    private val onItemClick: (HomeFeature) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = features.size

    override fun getItem(position: Int): HomeFeature = features[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_home_feature, parent, false)

        val feature = getItem(position)
        val cardView = view.findViewById<CardView>(R.id.featureCard)
        val titleText = view.findViewById<TextView>(R.id.featureTitle)

        titleText.text = feature.title
        cardView.setCardBackgroundColor(feature.color)

        cardView.setOnClickListener {
            onItemClick(feature)
        }

        return view
    }
}