package com.mcssoft.raceday.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mcssoft.raceday.database.entity.RaceMeeting
import com.mcssoft.raceday.databinding.ListItemMeetingHeaderBinding
import com.mcssoft.raceday.interfaces.IViewHolder
import com.mcssoft.raceday.utility.Constants.VIEW_TYPE_DETAIL

/**
 * ViewHolder for the RaceMeeting header.
 * @param binding: The layout view.
 */
class RaceMeetingHeaderViewHolder(private val binding: ListItemMeetingHeaderBinding, private val iViewHolder: IViewHolder)
    : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    init {
        binding.clickListener = this
    }

    internal fun bind(mtg: RaceMeeting) {
        binding.apply {
            meeting = mtg

            executePendingBindings()
        }
    }

    override fun onClick(view: View) {
        iViewHolder.onViewHolderSelect(VIEW_TYPE_DETAIL, adapterPosition)
    }

}