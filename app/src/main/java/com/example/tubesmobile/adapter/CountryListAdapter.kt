package com.example.tubesmobile.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tubesmobile.data.CountryItem
import com.example.tubesmobile.databinding.TaskItemBinding

class CountryListAdapter(
    private val countries: ArrayList<CountryItem>,
    val itemClickListener: (CountryItem) -> Unit
): RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    // create an inner class for ViewHolder
    inner class CountryViewHolder(private val binding: TaskItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        // bind the items with each item of the list
        // which than will be shown in recycler view
        fun bind(country: CountryItem) = with(binding) {
            taskName.text = country.countryName
            taskDeadline.text = country.countryArea
            root.setOnClickListener { itemClickListener(country) }
        }
    }

    // inside the onCreateViewHolder inflate the view of CountryItemBinding
    // and return new ViewHolder object containing this layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CountryViewHolder(binding)
    }

    // in OnBindViewHolder this is where we get the current item
    // and bind it to the layout
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.bind(country)
    }

    // return the size of ArrayList
    override fun getItemCount() = countries.size
}