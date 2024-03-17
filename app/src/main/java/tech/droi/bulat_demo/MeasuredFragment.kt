package tech.droi.bulat_demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import tech.droi.bulat_demo.MainActivity.Companion.FORMAT2
import tech.droi.bulat_demo.databinding.FragmentMeasuredBinding

class MeasuredFragment : Fragment() {
    private val model: SharedViewModel by activityViewModels()
    private var _binding: FragmentMeasuredBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMeasuredBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.activePower.observe(viewLifecycleOwner) {
            binding.tvMeasuredPowerActive.text = String.format(FORMAT2, it)
        }
        model.reactivePower.observe(viewLifecycleOwner) {
            binding.tvMeasuredPowerReactive.text = String.format(FORMAT2, it)
        }
        model.voltagePhase.observe(viewLifecycleOwner) {
            binding.tvMeasuredPhaseVoltage.text = String.format(FORMAT2, it)
        }
        model.currentPhase.observe(viewLifecycleOwner) {
            binding.tvMeasuredPhaseCurrent.text = String.format(FORMAT2, it)
        }
        model.currentNeutral.observe(viewLifecycleOwner) {
            binding.tvMeasuredNeutralCurrent.text = String.format(FORMAT2, it)
        }
        model.frequency.observe(viewLifecycleOwner) {
            binding.tvMeasuredFrequency.text = String.format(FORMAT2, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}