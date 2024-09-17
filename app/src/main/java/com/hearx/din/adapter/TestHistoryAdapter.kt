package com.hearx.din.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hearx.din.R
import com.hearx.din.database.TestHistoryTable

class TestHistoryAdapter(private val context: Context, private val historyList: List<TestHistoryTable>) : RecyclerView.Adapter<TestHistoryAdapter.TestHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHistoryAdapter.TestHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_history_item, parent, false)
        return TestHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestHistoryAdapter.TestHistoryViewHolder, position: Int) {
        val history = historyList[position]
        val date = context.getString(R.string.test_was_taken_on, history.testDate)
        val coordinates = context.getString(R.string.your_score_was, history.score)
        holder.date.text = date
        holder.score.text = coordinates
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class TestHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date: TextView = itemView.findViewById(R.id.history_date)
        var score: TextView = itemView.findViewById(R.id.history_score)
    }
}