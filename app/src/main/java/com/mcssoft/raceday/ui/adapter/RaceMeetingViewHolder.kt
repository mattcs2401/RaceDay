package com.mcssoft.raceday.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.mcssoft.raceday.database.entity.RaceMeeting
import com.mcssoft.raceday.databinding.ListItemMeetingBinding

/**
 * ViewHolder for the RaceMeeting.
 * @param binding: The layout view.
 */
class RaceMeetingViewHolder(private val binding: ListItemMeetingBinding)
    : RecyclerView.ViewHolder(binding.root) {

    internal fun bind(mtg: RaceMeeting) {
        binding.apply {
            meeting = mtg
            executePendingBindings()
        }
    }

}