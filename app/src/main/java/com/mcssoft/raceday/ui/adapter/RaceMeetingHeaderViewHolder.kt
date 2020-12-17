package com.mcssoft.raceday.ui.adapter

import android.view.View
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mcssoft.raceday.database.entity.RaceMeeting
import com.mcssoft.raceday.databinding.ListItemMeetingHeaderBinding
import com.mcssoft.raceday.interfaces.IAdapter
import com.mcssoft.raceday.utility.Constants.VIEW_TYPE_DETAIL
import com.mcssoft.raceday.utility.Constants.VIEW_TYPE_HEADER

/**
 * ViewHolder for the RaceMeeting header.
 * @param binding: The layout view.
 */
class RaceMeetingHeaderViewHolder(private val binding: ListItemMeetingHeaderBinding, private val iAdapter: IAdapter)
    : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private lateinit var arrow: ImageView

    init {
        arrow =  binding.idIvArrorw
        arrow.setOnClickListener(this)
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
        when(binding.meeting?.meta) {
            false -> {
                // Expand.
                iAdapter.onTypeSelect(VIEW_TYPE_DETAIL, adapterPosition)
                animateExpand()

            }
            true -> {
                // Collapse.
                iAdapter.onTypeSelect(VIEW_TYPE_HEADER, adapterPosition)
                animateCollapse()
            }
        }
        // Testing only.
        Toast.makeText(view.context, "Clicked on the arrow. Meeting code=" +
                binding.meeting?.meetingCode, Toast.LENGTH_SHORT).show()
    }

    private fun animateExpand() {
        val rotate = RotateAnimation(360F, 180F, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.duration = 300;
        rotate.fillAfter = true;
        arrow.setAnimation(rotate);
    }

    private fun animateCollapse() {
        val rotate = RotateAnimation(180F, 360F, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.duration = 300;
        rotate.fillAfter = true;
        arrow.setAnimation(rotate);
    }
}