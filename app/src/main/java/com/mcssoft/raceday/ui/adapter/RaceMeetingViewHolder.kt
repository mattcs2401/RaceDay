package com.mcssoft.raceday.ui.adapter

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mcssoft.raceday.database.entity.RaceMeeting
import com.mcssoft.raceday.databinding.ListItemMeetingBinding

/**
 * ViewHolder for the RaceMeeting.
 * @param binding: The layout view.
 */
class RaceMeetingViewHolder(private val binding: ListItemMeetingBinding)
    : RecyclerView.ViewHolder(binding.root) {//}, View.OnClickListener {

    init {
        binding.setClickListener {
            binding.meeting.let {meeting ->
                Toast.makeText(it.context, "Clicked Meeting code: " + meeting?.meetingCode +
                        " " + meeting?.venueName, Toast.LENGTH_SHORT).show()
            }
        }
    }

    internal fun bind(mtg: RaceMeeting) {
        binding.apply {
            meeting = mtg

            executePendingBindings()
        }
    }

//    override fun onClick(view: View) {
//        //TODO("Not yet implemented")
//    }
}