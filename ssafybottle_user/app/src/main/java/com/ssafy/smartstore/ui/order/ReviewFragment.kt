package com.ssafy.smartstore.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ssafy.smartstore.data.dto.comment.CommentDto
import com.ssafy.smartstore.data.dto.product.ProductCommentDto
import com.ssafy.smartstore.databinding.FragmentReviewBinding
import com.ssafy.smartstore.utils.COMMENT
import com.ssafy.smartstore.utils.retrofit.FetchState
import com.ssafy.smartstore.utils.PRODUCT_ID
import com.ssafy.smartstore.utils.getUserId

class ReviewFragment : Fragment() {

    private var _binding: FragmentReviewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReviewViewModel by viewModels()
    private var productId: Int = -1
    private var comment: ProductCommentDto? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        setOnClickListeners()
        registerObserver()
    }

    private fun setOnClickListeners() {
        binding.btnReviewRegister.setOnClickListener {
            if (binding.edtReviewContent.text.toString().isBlank()) {
                Toast.makeText(requireContext(), "상품평 작성란이 비었습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 새로 등록
            if (comment == null) {
                val comment = CommentDto(
                    comment = binding.edtReviewContent.text.toString(),
                    id = 0,
                    productId = productId,
                    rating = binding.ratingbarReviewRating.rating.toInt() * 2,
                    userId = getUserId()
                )
                viewModel.insertComment(comment)
            } else {
                // 수정
                val comment = CommentDto(
                    comment = binding.edtReviewContent.text.toString(),
                    id = comment!!.commentId,
                    productId = productId,
                    rating = binding.ratingbarReviewRating.rating.toInt() * 2,
                    userId = getUserId()
                )
                viewModel.updateComment(comment)
            }
        }
        binding.imgReviewBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun initData() {
        productId = requireArguments().getInt(PRODUCT_ID, -1)
        comment = requireArguments().getSerializable(COMMENT) as ProductCommentDto?

        // 수정
        if (comment != null) {
            comment = requireArguments().getSerializable(COMMENT) as ProductCommentDto?
            comment?.let {
                initView()
            }
        }
    }

    private fun initView() {
        binding.edtReviewContent.setText(comment!!.comment)
        binding.ratingbarReviewRating.rating = comment!!.rating.toFloat()
    }

    private fun registerObserver() {
        viewModel.isPosted.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "상품평 등록에 성공했습니다.", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(requireContext(), "상품평 등록에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.isUpdated.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "상품평 수정에 성공했습니다.", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(requireContext(), "상품평 수정에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.fetchState.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
            when (it) {
                FetchState.BAD_INTERNET -> {
                }
                FetchState.PARSE_ERROR -> {
                }
                FetchState.WRONG_CONNECTION -> {
                }
                FetchState.FAIL -> {
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}