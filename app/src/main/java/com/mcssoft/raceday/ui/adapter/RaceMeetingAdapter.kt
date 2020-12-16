package com.mcssoft.raceday.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mcssoft.raceday.database.entity.RaceMeeting
import com.mcssoft.raceday.databinding.ListItemMeetingHeaderBinding
import javax.inject.Inject

/**
 * Class implements the RaceMeeting list adapter.
 * @Note: Inject constructor simply denotes injectable class without having to use a Provides
 *        annotated method.
 */
class RaceMeetingAdapter @Inject constructor()
    : ListAdapter<RaceMeeting, RecyclerView.ViewHolder>(RaceMeetingDiffCallback()) {

    //https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        Log.d("TAG","RaceMeetingAdapter.onCreateViewHolder")
        return when(viewType) {
            ITEM_VIEW_TYPE_HEADER -> {
                RaceMeetingHeaderViewHolder(
                    ListItemMeetingHeaderBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false))
            }
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            ITEM_VIEW_TYPE_HEADER -> {
                holder as RaceMeetingHeaderViewHolder
                holder.bind(getItem(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val bp = "bp"
        // Testing.
        return ITEM_VIEW_TYPE_HEADER
        // This method uses a property of the item to determine which item it is,i.e. header or
        // detail (for this project).
        // Does this all come back to the model?

        //return super.getItemViewType(position)
    }

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_ITEM = 1
}

private class RaceMeetingDiffCallback : DiffUtil.ItemCallback<RaceMeeting>() {

    override fun areItemsTheSame(oldItem: RaceMeeting, newItem: RaceMeeting): Boolean {
        return oldItem.mtgId == newItem.mtgId
    }

    override fun areContentsTheSame(oldItem: RaceMeeting, newItem: RaceMeeting): Boolean {
        return oldItem == newItem
    }
}
