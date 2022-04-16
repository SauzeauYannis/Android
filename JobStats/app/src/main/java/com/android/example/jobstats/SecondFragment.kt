package com.android.example.jobstats

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.android.example.jobstats.databinding.FragmentSecondBinding


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var tip = false

        binding.boolenPouboire.setOnClickListener {
            if (tip) {
                binding.boolenPouboire.setText(R.string.non)
                binding.nombrePourboire.setText(R.string.zero)
                binding.nombrePourboire.visibility = View.INVISIBLE
                tip = false
            } else {
                binding.boolenPouboire.setText(R.string.oui)
                binding.nombrePourboire.visibility = View.VISIBLE
                tip = true
            }
        }

        binding.ajouter.setOnClickListener {
            if (isCorrect()) {
                addTrip()
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
        }

        binding.barrePersonnes.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                changeNumber()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        val spinner: Spinner = binding.listeDuree

        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.duree,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun changeNumber() {
        binding.valeurBarre.text = binding.barrePersonnes.progress.toString()
    }

    private fun addTrip() {
        val cardinaud = binding.cardinaud.isChecked
        val numberPerson = binding.valeurBarre.text.toString().toInt()
        val collective = binding.collective.isChecked
        val time = binding.listeDuree.getItemAtPosition(binding.listeDuree.selectedItemPosition).toString()
        val tip = binding.boolenPouboire.isChecked
        val tipValue = binding.nombrePourboire.text.toString().toFloat()

        val t = when (time) {
            "0H45" -> 45
            "1H00" -> 60
            "1H30" -> 90
            "3H00" -> 180
            else -> 120
        }

        val sharedPreferences = this.activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        val savedTrip = sharedPreferences.getInt(R.string.saved_trip.toString(), 0)
        val savedCard = sharedPreferences.getInt(R.string.saved_card.toString(), 0)
        val savedPrad= sharedPreferences.getInt(R.string.saved_prad.toString(), 0)
        val savedPers= sharedPreferences.getInt(R.string.saved_pers.toString(), 0)
        val savedCole= sharedPreferences.getInt(R.string.saved_cole.toString(), 0)
        val savedPriv= sharedPreferences.getInt(R.string.saved_priv.toString(), 0)
        val savedTime= sharedPreferences.getInt(R.string.saved_time.toString(), 0)
        val savedTip= sharedPreferences.getInt(R.string.saved_tip.toString(), 0)
        val savedTipValue= sharedPreferences.getFloat(R.string.saved_tip_value.toString(), 0F)

        with (sharedPreferences.edit()) {
            putInt(R.string.saved_trip.toString(), savedTrip + 1)
            if (cardinaud) {
                putInt(R.string.saved_card.toString(), savedCard + 1)
            } else {
                putInt(R.string.saved_prad.toString(), savedPrad + 1)
            }
            putInt(R.string.saved_pers.toString(), savedPers + numberPerson)
            if (collective) {
                putInt(R.string.saved_cole.toString(), savedCole + 1)
            } else {
                putInt(R.string.saved_priv.toString(), savedPriv + 1)
            }
            putInt(R.string.saved_time.toString(), savedTime + t)
            if (tip) {
                putInt(R.string.saved_tip.toString(), savedTip + 1)
                putFloat(R.string.saved_tip_value.toString(), savedTipValue + tipValue)
            }
            commit()
        }
    }

    private fun isCorrect() : Boolean {
        val minOnePerson = binding.valeurBarre.text.toString().toInt() > 0

        return if (minOnePerson) {
            if (binding.boolenPouboire.isChecked) {
                binding.nombrePourboire.text.toString().toFloat() > 0F
            } else {
                true
            }
        } else {
            false
        }
    }
}
