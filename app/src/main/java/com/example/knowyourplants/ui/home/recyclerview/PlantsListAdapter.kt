package com.example.knowyourplants.ui.home.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.knowyourplants.R
import com.example.knowyourplants.data.remote.models.plant.PlantListItem

class PlantsListAdapter():
    RecyclerView.Adapter<PlantsListAdapter.ViewHolder>() {
    private val data: MutableList<PlantListItem> = mutableListOf()
    fun addData(list: List<PlantListItem>){
        val pos = data.size-1
        data.addAll(list)
        notifyItemRangeInserted(pos+1, list.size)
    }

    fun setData(list: List<PlantListItem>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_plant, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.tvPlantName.text = data[position].commonName
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvPlantName: TextView
        init {
            tvPlantName = view.findViewById(R.id.tv_plantName)
        }
    }
}