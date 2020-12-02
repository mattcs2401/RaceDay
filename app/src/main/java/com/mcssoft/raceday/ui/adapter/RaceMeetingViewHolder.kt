package com.mcssoft.raceday.ui.adapter

import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.mcssoft.raceday.R
import com.mcssoft.raceday.database.entity.RaceMeeting
import com.mcssoft.raceday.interfaces.IAdapter

/**
 * ViewHolder for the RaceMeeting.
 * @param view: The layout view.
 * @param iAdapter: Interface with the RaceMeetingAdapter.
 */
class RaceMeetingViewHolder(private val view: View, private val iAdapter: IAdapter)
    : RecyclerView.ViewHolder(view), View.OnClickListener/*, PopupMenu.OnMenuItemClickListener*/ {


    override fun onClick(view: View) {
        //TODO("Not yet implemented")
    }

    //<editor-fold default state="collapsed" desc="Region: Utility">
    /**
     * Utility method to setup the display.
     */
    internal fun bind(race: RaceMeeting) {
        // Instantiate the view holder components.
        setDisplayElements()
        // Set view holder component values.
        setDisplayValues(race)
    }

    /**
     * Utility method to bind the elements of the layout View.
     */
    private fun setDisplayElements() {
        // TBA.
    }

    /**
     * Utility method to set the element's display values.
     */
    private fun setDisplayValues(race: RaceMeeting) {
        // TBA
    }
    //</editor-fold>

}