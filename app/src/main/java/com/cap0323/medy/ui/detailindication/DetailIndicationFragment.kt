package com.cap0323.medy.ui.detailindication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cap0323.medy.databinding.FragmentDetailIndicationItemBinding

class DetailIndicationFragment : Fragment() {
    private lateinit var binding: FragmentDetailIndicationItemBinding

    companion object {
        const val category = "category"

        fun newInstance(categoryInject: String?): DetailIndicationFragment {
            val detailIndication = DetailIndicationFragment()

            val bundle: Bundle = Bundle().apply {
                putString(category, categoryInject)
            }
            detailIndication.arguments = bundle
            return detailIndication
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val name = arguments?.getString(category)

        if (name != null) {

        }
        Log.d("data from activity", name.toString())

        binding = FragmentDetailIndicationItemBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}