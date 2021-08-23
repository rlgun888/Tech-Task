package com.ryangunn.techtask.driver

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ryangunn.techtask.R
import com.ryangunn.techtask.databinding.ListItemDriverBinding
import com.ryangunn.techtask.driver.model.Driver

class DriverAdapter(val drivers: List<Driver>) :
    RecyclerView.Adapter<DriverAdapter.DriverViewHolder>() {
    var onDriverClick: ((Driver) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        val inflater = LayoutInflater.from(parent.getContext())
        return DriverViewHolder(ListItemDriverBinding.inflate(inflater))
    }

    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
        val driverAtPosition = drivers[position]
        holder.bind(driverAtPosition)
        holder.itemView.setOnClickListener {
            onDriverClick?.invoke(driverAtPosition)
        }
    }

    override fun getItemCount(): Int {
        return drivers.size
    }

    inner class DriverViewHolder(private val binding: ListItemDriverBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(driver: Driver) {
            binding.apply {
                driverNameTextView.text = String.format(
                    itemView.context.getString(R.string.driver_name),
                    driver.getFullName()
                )
                driverPlateTextView.text = String.format(
                    itemView.context.getString(R.string.driver_plate),
                    driver.driverDetails.plateNumber
                )
            }
        }
    }
}