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
 * Class implements the RaceMeeting list adapter.
 * @Note: Inject constructor simply denotes injectable class without having to use a Provides
 *        annotated method.
 */
class RaceMeetingAdapter @Inject constructor()
    : ListAdapter<RaceMeeting, RaceMeetingViewHolder>(RaceMeetingDiffCallback()) {

    //https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceMeetingViewHolder {
//        Log.d("TAG","RaceMeetingAdapter.onCreateViewHolder")
        return RaceMeetingViewHolder(
            ListItemMeetingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RaceMeetingViewHolder, position: Int) {
//        Log.d("TAG", "RaceDetailsAdapter.onBindViewHolder")
        holder.bind(getItem(position))
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
