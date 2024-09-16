package com.hearx.din.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hearx.din.R
import com.hearx.din.database.TestHistoryTable

class TestHistoryAdapter(private val historyList: List<TestHistoryTable>): RecyclerView.Adapter<TestHistoryAdapter.TestHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHistoryAdapter.TestHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_history_item, parent, false)
        return TestHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestHistoryAdapter.TestHistoryViewHolder, position: Int) {
        val history = historyList[position]
        val date = "Test was taken on: ${history.testDate}"
        val coordinates = "Your score was: ${history.score}"
        val tripletPlayed = "Last triplet played was: ${history.played}"
        val tripletAnswered = "Last triplet answered was: ${history.answered}"
        holder.date.text = date
        holder.played.text = tripletPlayed
        holder.answered.text = tripletAnswered
        holder.score.text = coordinates
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class TestHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date: TextView = itemView.findViewById(R.id.history_date)
        var score: TextView = itemView.findViewById(R.id.history_score)
        var played: TextView = itemView.findViewById(R.id.history_played)
        var answered: TextView = itemView.findViewById(R.id.history_answered)
    }
}