package com.ssafy.smartstore.ui.dlg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ssafy.smartstore.databinding.DialogRatingBinding

class RatingDialog(val confirmLogic : (rating: Float) -> Unit) : DialogFragment() {

    private var _binding: DialogRatingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogRatingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.textDlgRatingCancel.setOnClickListener { dismiss() }
        binding.textDlgRatingConfirm.setOnClickListener {
            confirmLogic(binding.ratingbarDlgRatingRating.rating)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}