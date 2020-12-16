package com.mcssoft.raceday.ui.adapter

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mcssoft.raceday.database.entity.RaceMeeting
import com.mcssoft.raceday.databinding.ListItemMeetingHeaderBinding

/**
 * ViewHolder for the RaceMeeting.
 * @param binding: The layout view.
 */
class RaceMeetingHeaderViewHolder(private val binding: ListItemMeetingHeaderBinding)
    : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    init {
        binding.idIvArrorw.setOnClickListener(this)
//        {
//            Toast.makeText(it.context, "Clicked on the arrow. Meeting code=" + binding.meeting?.meetingCode, Toast.LENGTH_SHORT).show()
//        }

//        binding.setClickListener {
//            binding.meeting.let {meeting ->
//                Toast.makeText(it.context, "Clicked Meeting code: " + meeting?.meetingCode +
//                        " " + meeting?.venueName, Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    internal fun bind(mtg: RaceMeeting) {
        binding.apply {
            meeting = mtg

            executePendingBindings()
        }
    }

    override fun onClick(view: View) {
        Toast.makeText(view.context, "Clicked on the arrow. Meeting code=" +
                binding.meeting?.meetingCode, Toast.LENGTH_SHORT).show()
//        //TODO("Not yet implemented")
    }
}