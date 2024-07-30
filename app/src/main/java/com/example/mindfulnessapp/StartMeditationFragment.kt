package com.example.mindfulnessapp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class StartMeditationFragment : Fragment() {

    private lateinit var spinnerMeditationType: Spinner
    private lateinit var radioGroupDuration: RadioGroup
    private lateinit var editTextReflections: EditText
    private lateinit var buttonStart: Button
    private lateinit var textViewTimer: TextView
    private var countdownTimer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start_meditation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinnerMeditationType = view.findViewById(R.id.spinner_meditation_type)
        radioGroupDuration = view.findViewById(R.id.radio_group_duration)
        editTextReflections = view.findViewById(R.id.edittext_reflections)
        buttonStart = view.findViewById(R.id.button_start)
        textViewTimer = view.findViewById(R.id.textview_timer)

        buttonStart.setOnClickListener { startMeditation() }
    }

    private fun startMeditation() {
        val selectedDurationId = radioGroupDuration.checkedRadioButtonId
        val durationInMinutes = when (selectedDurationId) {
            R.id.radio_5_minutes -> 5
            R.id.radio_10_minutes -> 10
            R.id.radio_15_minutes -> 15
            else -> 0
        }

        if (durationInMinutes > 0) {
            val sharedPreferences = requireContext().getSharedPreferences("MeditationPrefs", 0)
            val editor = sharedPreferences.edit()

            // Save reflection and duration
            editor.putString("reflection", editTextReflections.text.toString())
            editor.putInt("duration", durationInMinutes)
            editor.apply()

            val durationInMillis = durationInMinutes * 60 * 1000
            countdownTimer?.cancel()
            countdownTimer = object : CountDownTimer(durationInMillis.toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val minutes = (millisUntilFinished / 1000) / 60
                    val seconds = (millisUntilFinished / 1000) % 60
                    textViewTimer.text = String.format("%02d:%02d", minutes, seconds)
                }

                override fun onFinish() {
                    textViewTimer.text = "00:00"
                    Toast.makeText(
                        requireContext(),
                        "Meditation session finished!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }.start()
        } else {
            Toast.makeText(requireContext(), "Please select a duration", Toast.LENGTH_SHORT).show()
        }
    }
}
