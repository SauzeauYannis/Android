package com.android.example.jobstats

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.example.jobstats.databinding.FragmentFirstBinding
import kotlin.math.truncate


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        val s: String =
            "Nombre de ballades :  " + savedTrip.toString() + "\n\n\n" +
                    "Cardinaud :  " + savedCard.toString() + percentage(savedCard, savedTrip) + "\n\n" +
                    "Prada :  " + savedPrad.toString() + percentage(savedPrad, savedTrip) + "\n\n" +
                    "Nombre de clients :  " + savedPers.toString() + average(savedPers.toFloat(), savedTrip.toFloat()) + "\n\n" +
                    "Collective :  " + savedCole.toString() + percentage(savedCole, savedTrip) + "\n\n" +
                    "Privative :  " + savedPriv.toString() + percentage(savedPriv, savedTrip) + "\n\n" +
                    "Temps :  " + time(savedTime) + averageTime(savedTime, savedTrip) + "\n\n" +
                    "Nombre de pourboires :  " + savedTip.toString() + percentage(savedTip, savedTrip) + "\n\n" +
                    "Valeur de pourboires :  " + "%.2f".format(savedTipValue) + average(savedTipValue, savedTrip.toFloat())

        binding.textView.text = s

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun percentage(num : Int, total : Int) : String {
        return if (total == 0) {
            "  ( 0% )"
        } else {
            val n = num.toFloat()
            val t = total.toFloat()
            val result = (n / t) * 100
            val s = "%.2f".format(result)
            "  ( $s% )"
        }
    }

    private fun average(num : Float, total : Float) : String {
        return if (total.equals(0F)) {
            "  ( Moy : 0 )"
        } else {
            val result = num / total
            val s = "%.2f".format(result)
            "  ( Moy : $s )"
        }
    }

    private fun time(time : Int) : String {
        val hours : Float = time / 60F
        val min : Int = time % 60
        return "%.0f".format(truncate(hours)) + "H" + min.toString()
    }

    private fun averageTime(num : Int, total: Int) : String {
        return if (total == 0) {
            "  ( Moy : 0H00 )"
        } else {
            val result = num / total
            val s = time(result)
            "  ( Moy : $s )"
        }
    }
}