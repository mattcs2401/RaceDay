package com.mcssoft.raceday.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mcssoft.raceday.database.entity.RaceMeeting
import com.mcssoft.raceday.databinding.ListItemMeetingBinding
import javax.inject.Inject

/**
 * Class implements the RaceDMeeting recycler view adapter.
 */
class RaceMeetingAdapter @Inject constructor()
    : ListAdapter<RaceMeeting, RaceMeetingViewHolder>(RaceMeetingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceMeetingViewHolder {
//        Log.d("TAG","RaceMeetingAdapter.onCreateViewHolder")
        val view = ListItemMeetingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return RaceMeetingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RaceMeetingViewHolder, position: Int) {
        val meeting = getItem(position)
        holder.bind(meeting)
        Log.d("TAG", """RaceDetailsAdapter.onBindViewHolder:meetingId=${meeting.mtgId}""")
    }

}

private class RaceMeetingDiffCallback : DiffUtil.ItemCallback<RaceMeeting>() {

    override fun areItemsTheSame(oldItem: RaceMeeting, newItem: RaceMeeting): Boolean {
        return oldItem.mtgId == newItem.mtgId
    }

    override fun areContentsTheSame(oldItem: RaceMeeting, newItem: RaceMeeting): Boolean {
        return oldItem == newItem
    }
}
