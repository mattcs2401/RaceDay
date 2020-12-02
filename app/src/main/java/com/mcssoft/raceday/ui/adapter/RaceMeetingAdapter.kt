package com.mcssoft.raceday.ui.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mcssoft.raceday.interfaces.IAdapter
import com.mcssoft.raceday.interfaces.IMain

/**
 * Class implements the RaceDMeeting recycler view adapter.
 * @param iMain: Interface with the MainFragment.
 */
class RaceMeetingAdapter(private val iMain: IMain) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), IAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("TAG","RaceMeetingAdapter.onCreateViewHolder")
        val view: View = View(parent.context)  // <<-- temporary
//                LayoutInflater.from(parent.context).inflate(R.layout.row_race_reveal, parent, false)
        return RaceMeetingViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RaceMeetingViewHolder).bind(iMain.getAt(position))
        Log.d("TAG","RaceDetailsAdapter.onBindViewHolder")    }

    override fun getItemCount(): Int {
        return iMain.getRaceMeetingCount()
    }

    //<editor-fold default state="collapsed" desc="Region: IAdapter">
    override fun delete(position: Int) {
//        iMain.deleteAt(position)
        notifyItemRemoved(position)
    }

    override fun onTypeSelect(selType: Int, position: Int) {
//        iMain.onTypeSelect(selType, position)
    }
    //</editor-fold>
}