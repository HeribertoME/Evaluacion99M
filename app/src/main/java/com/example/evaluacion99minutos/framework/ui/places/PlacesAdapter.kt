package com.example.evaluacion99minutos.framework.ui.places

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluacion99minutos.R
import com.example.evaluacion99minutos.data.database.Place
import com.example.evaluacion99minutos.databinding.PlaceItemBinding

class PlacesAdapter(
    private val onPlaceClick: (Place) -> Unit,
    private val onPlaceDelete: (Place) -> Unit
) : ListAdapter<Place, PlaceViewHolder>(PlaceDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.place_item, parent, false
        )
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(getItem(position), onPlaceClick, onPlaceDelete)
    }

}

class PlaceDiffCallback : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem == newItem
    }

}

class PlaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = PlaceItemBinding.bind(view)

    fun bind(place: Place, onPlaceClick: (Place) -> Unit, onPlaceDelete: (Place) -> Unit) {
        binding.tvName.text = place.name
        binding.tvDescription.text = place.description
        binding.root.setOnClickListener { onPlaceClick(place) }
        binding.ivDelete.setOnClickListener { onPlaceDelete(place) }
    }
}