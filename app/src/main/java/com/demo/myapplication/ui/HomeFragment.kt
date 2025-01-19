package com.demo.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.demo.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val TAB_PARAM="gparam"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private var param: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            param = it.getString(TAB_PARAM)
        }
        binding?.let {
            it.tvHomeContent?.text = "I'am $param page"
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(TAB_PARAM, param)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}