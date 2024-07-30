package com.example.mindfulnessapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class MeditationLogFragment : Fragment() {

    private lateinit var textViewLog: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meditation_log, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewLog = view.findViewById(R.id.textview_log)
        loadMeditationLog()
    }

    private fun loadMeditationLog() {
        val sharedPreferences = requireContext().getSharedPreferences("MeditationPrefs", 0)
        val reflection = sharedPreferences.getString("reflection", "No reflections")
        val duration = sharedPreferences.getInt("duration", 0)

        textViewLog.text = "Reflection: $reflection\nDuration: $duration minutes"
    }
}
