package com.ssafy.smartstore.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.smartstore.R
import com.ssafy.smartstore.application.SmartStoreApplication.Companion.isOrder
import com.ssafy.smartstore.application.SmartStoreApplication.Companion.orderId
import com.ssafy.smartstore.data.dto.product.ProductDetailDto
import com.ssafy.smartstore.data.entitiy.Order
import com.ssafy.smartstore.data.entitiy.OrderDetail
import com.ssafy.smartstore.databinding.FragmentOrderBinding
import com.ssafy.smartstore.ui.adapter.CommentAdapter
import com.ssafy.smartstore.ui.adapter.OnItemClickListener
import com.ssafy.smartstore.utils.*
import com.ssafy.smartstore.utils.retrofit.FetchState

class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CommentAdapter
    private val viewModel: OrderViewModel by viewModels()
    private lateinit var userId: String
    private var productId = 0

    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = getUserId()
        initAdapter()
        registerObserver()
        setOnClickListeners()
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun initData() {
        productId = arguments?.getInt(PRODUCT_ID)!!
        viewModel.getProduct(productId)
    }

    private fun initAdapter() {
        adapter = CommentAdapter(userId, itemClickListener)

        binding.recyclerOrderComment.apply {
            this.adapter = this@OrderFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private val itemClickListener = object: OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            showPopMenu(view, position)
        }
    }

    private fun showPopMenu(view: View, position: Int) {
        val popupMenu = PopupMenu(requireContext(), view)
        requireActivity().menuInflater.inflate(R.menu.popupmenu_comment_more, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.popup_comment_delete -> {
                    viewModel.deleteComment(viewModel.comments.value!![position].commentId)
                }
                R.id.popup_comment_edit -> {
                    findNavController().navigate(R.id.action_orderFragment_to_reviewFragment, bundleOf(
                        PRODUCT_ID to productId, COMMENT to viewModel.comments.value!![position]))
                }
            }
            false
        }
        popupMenu.show()
    }

    private fun setProduct(product: ProductDetailDto) {
        binding.apply {
            textOrderCoffeename.text = product.name
            textOrderPrice.text = "${product.price}원"
            textOrderRating.text = "(${product.commentCnt})"
            ratingbarOrderRating.rating = product.avg
            imgOrderCoffee.setImageResource(requireView().getResourceId(product.img))
        }
    }

    private fun registerObserver() {
        viewModel.product.observe(viewLifecycleOwner) {
            setProduct(it)
        }
        viewModel.comments.observe(viewLifecycleOwner) { it ->
            adapter.apply {
                comments = it
                notifyDataSetChanged()
            }
        }
        viewModel.isDeleted.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "상품평을 삭제했습니다.", Toast.LENGTH_SHORT).show()
                initData()
            } else {
                Toast.makeText(requireContext(), "상품평을 삭제하는데 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isComplete.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "장바구니에 상품을 담았습니다.", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(requireContext(), "장바구니에 상품을 담는데 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.fetchState.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "상품정보를 받아오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
            when(it) {
                FetchState.BAD_INTERNET -> { }
                FetchState.PARSE_ERROR -> {}
                FetchState.WRONG_CONNECTION -> {}
                FetchState.FAIL -> {}
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imgOrderBack.setOnClickListener { requireActivity().onBackPressed() }
        binding.btnOrderReview.setOnClickListener {
            findNavController().navigate(R.id.action_orderFragment_to_reviewFragment, bundleOf(
                PRODUCT_ID to productId, COMMENT to null))
        }
        binding.imgOrderPlus.setOnClickListener {
            binding.textOrderQuantity.text = "${++count}"
        }
        binding.imgOrderMinus.setOnClickListener {
            if (count < 1) {
                return@setOnClickListener
            }
            binding.textOrderQuantity.text = "${--count}"
        }
        binding.btnOrderCart.setOnClickListener {
            if (binding.textOrderQuantity.text.toString().toInt() < 1) {
                Toast.makeText(requireContext(), "상품 수량을 확인해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (isOrder) { // 장바구니에 이미 주문이 있다면
                viewModel.insertOrder(
                    OrderDetail(
                        orderId = orderId,
                        productId = productId,
                        quantity = binding.textOrderQuantity.text.toString().toInt()
                    )
                )
            } else {
                viewModel.insertFirstOrder(
                    Order(userId = userId, orderTable = ""),
                    OrderDetail(
                        orderId = 0,
                        productId = productId,
                        quantity = binding.textOrderQuantity.text.toString().toInt()
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}