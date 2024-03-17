package tech.droi.bulat_demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import tech.droi.bulat_demo.databinding.FragmentPassportBinding

class PassportFragment : Fragment() {
    private val model: SharedViewModel by activityViewModels()
    private var _binding: FragmentPassportBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPassportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.manufacturer.observe(viewLifecycleOwner) {
            binding.tvPassportManufacturer.text = it
        }
        model.model.observe(viewLifecycleOwner) {
            binding.tvPassportModel.text = it
        }
        model.serial.observe(viewLifecycleOwner) {
            binding.tvPassportSerial.text = it
        }
        model.hardware.observe(viewLifecycleOwner) {
            binding.tvPassportHardware.text = it
        }
        model.firmware.observe(viewLifecycleOwner) {
            binding.tvPassportFirmware.text = it
        }
        model.software.observe(viewLifecycleOwner) {
            binding.tvPassportSoftware.text = it
        }
        model.application.observe(viewLifecycleOwner) {
            binding.tvPassportApplication.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}