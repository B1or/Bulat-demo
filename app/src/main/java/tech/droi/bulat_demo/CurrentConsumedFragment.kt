package tech.droi.bulat_demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.coroutines.*
import tech.droi.bulat_demo.MainActivity.Companion.FORMAT
import tech.droi.bulat_demo.databinding.FragmentConsumedCurrentBinding

class CurrentConsumedFragment : Fragment() {
    private val model: SharedViewModel by activityViewModels()
    private var _binding: FragmentConsumedCurrentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentConsumedCurrentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.activeForwardSum.observe(viewLifecycleOwner) {
            binding.tvCurrentSumForwardActive.text = String.format(FORMAT, it)
        }
        model.reactiveForwardSum.observe(viewLifecycleOwner) {
            binding.tvCurrentSumForwardReactive.text = String.format(FORMAT, it)
        }
        model.activeReverseSum.observe(viewLifecycleOwner) {
            binding.tvCurrentSumReverseActive.text = String.format(FORMAT, it)
        }
        model.reactiveReverseSum.observe(viewLifecycleOwner) {
            binding.tvCurrentSumReverseReactive.text = String.format(FORMAT, it)
        }
        model.activeForwardT1.observe(viewLifecycleOwner) {
            binding.tvCurrentT1ForwardActive.text = String.format(FORMAT, it)
        }
        model.reactiveForwardT1.observe(viewLifecycleOwner) {
            binding.tvCurrentT1ForwardReactive.text = String.format(FORMAT, it)
        }
        model.activeReverseT1.observe(viewLifecycleOwner) {
            binding.tvCurrentT1ReverseActive.text = String.format(FORMAT, it)
        }
        model.reactiveReverseT1.observe(viewLifecycleOwner) {
            binding.tvCurrentT1ReverseReactive.text = String.format(FORMAT, it)
        }
        model.activeForwardT2.observe(viewLifecycleOwner) {
            binding.tvCurrentT2ForwardActive.text = String.format(FORMAT, it)
        }
        model.reactiveForwardT2.observe(viewLifecycleOwner) {
            binding.tvCurrentT2ForwardReactive.text = String.format(FORMAT, it)
        }
        model.activeReverseT2.observe(viewLifecycleOwner) {
            binding.tvCurrentT2ReverseActive.text = String.format(FORMAT, it)
        }
        model.reactiveReverseT2.observe(viewLifecycleOwner) {
            binding.tvCurrentT2ReverseReactive.text = String.format(FORMAT, it)
        }
        model.activeForwardT3.observe(viewLifecycleOwner) {
            binding.tvCurrentT3ForwardActive.text = String.format(FORMAT, it)
        }
        model.reactiveForwardT3.observe(viewLifecycleOwner) {
            binding.tvCurrentT3ForwardReactive.text = String.format(FORMAT, it)
        }
        model.activeReverseT3.observe(viewLifecycleOwner) {
            binding.tvCurrentT3ReverseActive.text = String.format(FORMAT, it)
        }
        model.reactiveReverseT3.observe(viewLifecycleOwner) {
            binding.tvCurrentT3ReverseReactive.text = String.format(FORMAT, it)
        }
        model.activeForwardT4.observe(viewLifecycleOwner) {
            binding.tvCurrentT4ForwardActive.text = String.format(FORMAT, it)
        }
        model.reactiveForwardT4.observe(viewLifecycleOwner) {
            binding.tvCurrentT4ForwardReactive.text = String.format(FORMAT, it)
        }
        model.activeReverseT4.observe(viewLifecycleOwner) {
            binding.tvCurrentT4ReverseActive.text = String.format(FORMAT, it)
        }
        model.reactiveReverseT4.observe(viewLifecycleOwner) {
            binding.tvCurrentT4ReverseReactive.text = String.format(FORMAT, it)
        }
        binding.tvCurrentSumText.setOnClickListener {
            if (binding.tvCurrentSumForwardReactive.visibility == View.VISIBLE) {
                binding.tvCurrentSumForwardReactive.visibility = View.GONE
                binding.tvCurrentSumForwardReactiveText.visibility = View.GONE
                binding.tvCurrentSumReverseActive.visibility = View.GONE
                binding.tvCurrentSumReverseActiveText.visibility = View.GONE
                binding.tvCurrentSumReverseReactive.visibility = View.GONE
                binding.tvCurrentSumReverseReactiveText.visibility = View.GONE
                binding.tvCurrentSumText.setText(R.string.p_summary_)
            } else {
                binding.tvCurrentSumForwardReactive.visibility = View.VISIBLE
                binding.tvCurrentSumForwardReactiveText.visibility = View.VISIBLE
                binding.tvCurrentSumReverseActive.visibility = View.VISIBLE
                binding.tvCurrentSumReverseActiveText.visibility = View.VISIBLE
                binding.tvCurrentSumReverseReactive.visibility = View.VISIBLE
                binding.tvCurrentSumReverseReactiveText.visibility = View.VISIBLE
                binding.tvCurrentSumText.setText(R.string.m_summary_)
            }
        }
        binding.tvCurrentT1Text.setOnClickListener {
            if (binding.tvCurrentT1ForwardReactive.visibility == View.VISIBLE) {
                binding.tvCurrentT1ForwardReactive.visibility = View.GONE
                binding.tvCurrentT1ForwardReactiveText.visibility = View.GONE
                binding.tvCurrentT1ReverseActive.visibility = View.GONE
                binding.tvCurrentT1ReverseActiveText.visibility = View.GONE
                binding.tvCurrentT1ReverseReactive.visibility = View.GONE
                binding.tvCurrentT1ReverseReactiveText.visibility = View.GONE
                binding.tvCurrentT1Text.setText(R.string.p_t_1_)
            } else {
                binding.tvCurrentT1ForwardReactive.visibility = View.VISIBLE
                binding.tvCurrentT1ForwardReactiveText.visibility = View.VISIBLE
                binding.tvCurrentT1ReverseActive.visibility = View.VISIBLE
                binding.tvCurrentT1ReverseActiveText.visibility = View.VISIBLE
                binding.tvCurrentT1ReverseReactive.visibility = View.VISIBLE
                binding.tvCurrentT1ReverseReactiveText.visibility = View.VISIBLE
                binding.tvCurrentT1Text.setText(R.string.m_t_1_)
            }
        }
        binding.tvCurrentT2Text.setOnClickListener {
            if (binding.tvCurrentT2ForwardReactive.visibility == View.VISIBLE) {
                binding.tvCurrentT2ForwardReactive.visibility = View.GONE
                binding.tvCurrentT2ForwardReactiveText.visibility = View.GONE
                binding.tvCurrentT2ReverseActive.visibility = View.GONE
                binding.tvCurrentT2ReverseActiveText.visibility = View.GONE
                binding.tvCurrentT2ReverseReactive.visibility = View.GONE
                binding.tvCurrentT2ReverseReactiveText.visibility = View.GONE
                binding.tvCurrentT2Text.setText(R.string.p_t_2_)
            } else {
                binding.tvCurrentT2ForwardReactive.visibility = View.VISIBLE
                binding.tvCurrentT2ForwardReactiveText.visibility = View.VISIBLE
                binding.tvCurrentT2ReverseActive.visibility = View.VISIBLE
                binding.tvCurrentT2ReverseActiveText.visibility = View.VISIBLE
                binding.tvCurrentT2ReverseReactive.visibility = View.VISIBLE
                binding.tvCurrentT2ReverseReactiveText.visibility = View.VISIBLE
                binding.tvCurrentT2Text.setText(R.string.m_t_2_)
            }
        }
        binding.tvCurrentT3Text.setOnClickListener {
            if (binding.tvCurrentT3ForwardReactive.visibility == View.VISIBLE) {
                binding.tvCurrentT3ForwardReactive.visibility = View.GONE
                binding.tvCurrentT3ForwardReactiveText.visibility = View.GONE
                binding.tvCurrentT3ReverseActive.visibility = View.GONE
                binding.tvCurrentT3ReverseActiveText.visibility = View.GONE
                binding.tvCurrentT3ReverseReactive.visibility = View.GONE
                binding.tvCurrentT3ReverseReactiveText.visibility = View.GONE
                binding.tvCurrentT3Text.setText(R.string.p_t_3_)
            } else {
                binding.tvCurrentT3ForwardReactive.visibility = View.VISIBLE
                binding.tvCurrentT3ForwardReactiveText.visibility = View.VISIBLE
                binding.tvCurrentT3ReverseActive.visibility = View.VISIBLE
                binding.tvCurrentT3ReverseActiveText.visibility = View.VISIBLE
                binding.tvCurrentT3ReverseReactive.visibility = View.VISIBLE
                binding.tvCurrentT3ReverseReactiveText.visibility = View.VISIBLE
                binding.tvCurrentT3Text.setText(R.string.m_t_3_)
            }
        }
        binding.tvCurrentT4Text.setOnClickListener {
            if (binding.tvCurrentT4ForwardReactive.visibility == View.VISIBLE) {
                binding.tvCurrentT4ForwardReactive.visibility = View.GONE
                binding.tvCurrentT4ForwardReactiveText.visibility = View.GONE
                binding.tvCurrentT4ReverseActive.visibility = View.GONE
                binding.tvCurrentT4ReverseActiveText.visibility = View.GONE
                binding.tvCurrentT4ReverseReactive.visibility = View.GONE
                binding.tvCurrentT4ReverseReactiveText.visibility = View.GONE
                binding.tvCurrentT4Text.setText(R.string.p_t_4_)
            } else {
                binding.tvCurrentT4ForwardReactive.visibility = View.VISIBLE
                binding.tvCurrentT4ForwardReactiveText.visibility = View.VISIBLE
                binding.tvCurrentT4ReverseActive.visibility = View.VISIBLE
                binding.tvCurrentT4ReverseActiveText.visibility = View.VISIBLE
                binding.tvCurrentT4ReverseReactive.visibility = View.VISIBLE
                binding.tvCurrentT4ReverseReactiveText.visibility = View.VISIBLE
                binding.tvCurrentT4Text.setText(R.string.m_t_4_)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}