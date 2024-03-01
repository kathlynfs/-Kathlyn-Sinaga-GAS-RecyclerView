package com.bignerdranch.android.criminalintent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.criminalintent.databinding.ListItemNormalCrimeBinding
import com.bignerdranch.android.criminalintent.databinding.ListItemSeriousCrimeBinding
import java.lang.IllegalArgumentException


class NormalCrimeHolder(
    private val binding: ListItemNormalCrimeBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${crime.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.crimeSolved.visibility = if (crime.isSolved) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

class SeriousCrimeHolder(
    private val binding: ListItemSeriousCrimeBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()

        binding.root.setOnClickListener {
            Toast.makeText (
                binding.root.context,
                "${crime.title} clicked! It's a serious crime!",
                Toast.LENGTH_SHORT
            ).show()

        }

    }

}

class CrimeListAdapter(
    private val crimes: List<Crime>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val normalCrime = 0
    private val seriousCrime = 1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            normalCrime -> {
                val binding = ListItemNormalCrimeBinding.inflate(inflater, parent, false)
                NormalCrimeHolder(binding)
            }

            seriousCrime -> {
                val binding = ListItemSeriousCrimeBinding.inflate(inflater, parent, false)
                SeriousCrimeHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val crime = crimes[position]

        when (holder) {
            is NormalCrimeHolder -> holder.bind(crime)
            is SeriousCrimeHolder -> holder.bind(crime)
            else -> throw IllegalArgumentException("Invalid ViewHolder Type")

        }
    }

    override fun getItemCount() = crimes.size
}
