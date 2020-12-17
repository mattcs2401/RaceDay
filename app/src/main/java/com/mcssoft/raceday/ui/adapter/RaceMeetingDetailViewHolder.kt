package com.mcssoft.raceday.ui.adapter

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mcssoft.raceday.database.entity.RaceMeeting
import com.mcssoft.raceday.databinding.ListItemMeetingDetailBinding
import com.mcssoft.raceday.databinding.ListItemMeetingHeaderBinding

/**
 * ViewHolder for the RaceMeeting detail.
 * @param binding: The layout view.
 */
class RaceMeetingDetailViewHolder(private val binding: ListItemMeetingDetailBinding)
    : RecyclerView.ViewHolder(binding.root) {

    init {
        // TBA.
    }

    internal fun bind(mtg: RaceMeeting) {
        binding.apply {
            meeting = mtg

            executePendingBindings()
        }
    }

}