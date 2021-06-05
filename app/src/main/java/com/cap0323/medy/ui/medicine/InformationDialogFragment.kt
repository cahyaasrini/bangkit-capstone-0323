package com.cap0323.medy.ui.medicine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.cap0323.medy.databinding.FragmentInformationDialogBinding

class InformationDialogFragment(private val text: String) : DialogFragment() {
    private lateinit var binding: FragmentInformationDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInformationDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply{
            cancelBtn.setOnClickListener {
                dialog?.cancel()
            }
            textView2.text = text
        }
    }
}