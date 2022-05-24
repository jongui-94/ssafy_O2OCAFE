package com.ssafy.ssafybottle_manager.ui.root.sales

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.ssafybottle_manager.R
import com.ssafy.ssafybottle_manager.application.MainViewModel
import com.ssafy.ssafybottle_manager.data.dto.product.ProductDetailDto
import com.ssafy.ssafybottle_manager.databinding.FragmentProductDetailBinding
import com.ssafy.ssafybottle_manager.ui.adapter.CommentAdapter
import com.ssafy.ssafybottle_manager.ui.adapter.loadImage
import com.ssafy.ssafybottle_manager.utils.toMoney
import com.ssafy.ssafybottle_manager.utils.view.getResourceId

class ProductDetailFragment : Fragment() {

    private var _binding : FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var commentAdapter : CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTopLayoutTouch()
        initAdapter()
        observeData()
        setOnClickListeners()
    }

    fun getProductDetail(productId: Int) {
        mainViewModel.getProductDetail(productId)
    }

    private fun initAdapter() {
        commentAdapter = CommentAdapter().apply {
            onItemClickListener = commentItemClickListener
        }
        binding.recyclerProductdetailComment.apply {
            adapter = commentAdapter
        }
    }

    private val commentItemClickListener : (View, Int) -> Unit = { view, position ->
        showPopMenu(view, position)
    }

    private fun observeData() {
        mainViewModel.productDetail.observe(viewLifecycleOwner) {
            setProduct(it)
        }
        mainViewModel.comments.observe(viewLifecycleOwner) {
            commentAdapter.apply {
                comments = it
                notifyDataSetChanged()
            }
        }
    }

    private fun setProduct(product: ProductDetailDto) {
        binding.apply {
            textProductdetailCoffeename.text = product.name
            textProductdetailPrice.text = "${toMoney(product.price)}원"
            textProductdetailRating.text = "(${product.commentCnt})"
            ratingbarProductdetailRating.rating = product.avg / 2
            loadImage(imgProductdetailCoffee, product.img)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTopLayoutTouch() {
        binding.root.setOnTouchListener { _, _ ->
            true
        }
    }

    private fun setOnClickListeners() {
        binding.imgProductdetailClose.setOnClickListener {
            (parentFragment as SalesFragment).hideProductDetailFragment()
        }
    }

    private fun showPopMenu(view: View, position: Int) {
        val popupMenu = PopupMenu(requireContext(), view)
        requireActivity().menuInflater.inflate(R.menu.popupmenu_comment_more, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.popup_comment_delete -> {
                    //orderViewModel.deleteComment(orderViewModel.comments.value!![position].commentId)
                }
            }
            false
        }
        popupMenu.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}