package com.example.knowyourplants.ui.plant_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.knowyourplants.R
class PlantDetailsFragment : Fragment() {
    var plantId: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plant_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        plantId = arguments?.getInt("plantId")
        val tv = view.findViewById<TextView>(R.id.tv_plantId)
        plantId.let {
            tv.text = "Plant id is $plantId"
        }
    }
}