package com.bignerdranch.android.criminalintent

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.criminalintent.databinding.ListItemCrimeBinding
import com.bignerdranch.android.criminalintent.databinding.ListItemSeriousCrimeBinding
import java.util.UUID

// individual view holders for the recycler view
class CrimeHolder(
    private val binding: ListItemCrimeBinding
) : RecyclerView.ViewHolder(binding.root) {
    // bind to a specific crime
    private val normalCrimeView = 0
    private val seriousCrimeView = 1
    fun bind(crime: Crime, onCrimeClicked: (crimeId: UUID) -> Unit) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()

        // clicking on crime -> fragment
        binding.root.setOnClickListener {
            onCrimeClicked(crime.id)
        }

        binding.crimeSolved.visibility = if (crime.isSolved) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

// individual view holders for the recycler view
class SeriousCrimeHolder(
    private val binding: ListItemSeriousCrimeBinding
) : RecyclerView.ViewHolder(binding.root) {
    // bind to a specific crime
    fun bind(crime: Crime, onCrimeClicked: (crimeId: UUID) -> Unit) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()

        // clicking on crime -> fragment
        binding.root.setOnClickListener {
            onCrimeClicked(crime.id)
        }

        binding.crimeSolved.visibility = if (crime.isSolved) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}


class CrimeListAdapter(
    private val crimes: List<Crime>,
    private val onCrimeClicked: (crimeId: UUID) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // create a binding to display, wrap view in the view holder
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val normalCrimeView = 0
        val seriousCrimeView = 1

        return when (viewType) {
            // normal crime view
            normalCrimeView -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
                CrimeHolder(binding)
            }
            // serious crime view
            seriousCrimeView -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemSeriousCrimeBinding.inflate(inflater, parent, false)
                SeriousCrimeHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    // get proper crime to proper position
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val crime = crimes[position]
        when (holder) {
            is CrimeHolder -> holder.bind(crime, onCrimeClicked)
            is SeriousCrimeHolder -> holder.bind(crime, onCrimeClicked)
            else -> throw IllegalArgumentException("Invalid view holder type")
        }
    }

    // number of items in the list of crimes
    override fun getItemCount() = crimes.size

    override fun getItemViewType(position: Int) : Int {
        val normalCrimeView = 0
        val seriousCrimeView = 1

        Log.d("CrimeListAdapter", "Crime at position $position requires police: ${crimes[position].requiresPolice}")

        return if (crimes[position].requiresPolice) {
            seriousCrimeView
        } else {
            normalCrimeView
        }
    }

}
