package com.mcssoft.raceday.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mcssoft.raceday.database.entity.RaceMeeting
import com.mcssoft.raceday.databinding.ListItemMeetingDetailBinding
import com.mcssoft.raceday.interfaces.IViewHolder
import com.mcssoft.raceday.utility.Constants

/**
 * ViewHolder for the RaceMeeting detail.
 * @param binding: The layout view.
 */
class RaceMeetingDetailViewHolder(private val binding: ListItemMeetingDetailBinding, private val iViewHolder: IViewHolder)
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
        iViewHolder.onViewHolderSelect(Constants.VIEW_TYPE_HEADER, adapterPosition)
    }

}