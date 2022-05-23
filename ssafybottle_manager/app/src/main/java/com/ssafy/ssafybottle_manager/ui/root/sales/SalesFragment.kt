package com.ssafy.ssafybottle_manager.ui.root.sales

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.ssafy.ssafybottle_manager.application.MainViewModel
import com.ssafy.ssafybottle_manager.data.dto.product.ProductSalesDto
import com.ssafy.ssafybottle_manager.databinding.FragmentSalesBinding
import com.ssafy.ssafybottle_manager.utils.toMoney

class SalesFragment : Fragment() {
    private var _binding: FragmentSalesBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSalesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPieChart()
        initBarChart()
        initData()
        observeData()
        otherListeners()
    }

    private fun initData() {
        mainViewModel.getTotalSales()
        mainViewModel.getProductSales()
    }

    private fun observeData() {
        mainViewModel.totalSales.observe(viewLifecycleOwner) {
            binding.textSalesCost.text = "${toMoney(it)}원"
        }
        mainViewModel.productSalesList.observe(viewLifecycleOwner) {
            analyzeData(it)
        }
    }

    private fun analyzeData(salesList: List<ProductSalesDto>) {
        var totalBeverage = 0
        var totalDessert = 0

        salesList.forEach {
            if (it.type == "coffee") {
                totalBeverage += it.sales
            } else {
                totalDessert += it.sales
            }
        }

        setDataTotalCost(totalBeverage, totalDessert)
        setDataPieChart(totalBeverage, totalDessert)
        setDataBarChart(salesList)
    }

    private fun setDataTotalCost(totalBeverage: Int, totalDessert: Int) {
        binding.apply {
            textSalesBeverage.text = "${toMoney(totalBeverage)}원"
            textSalesDessert.text = "${toMoney(totalDessert)}원"
        }
    }

    private fun setDataPieChart(totalBeverage: Int, totalDessert: Int) {
        val pieEntries = mutableListOf<PieEntry>()
        pieEntries.add(PieEntry(totalBeverage.toFloat(), "음료"))
        pieEntries.add(PieEntry(totalDessert.toFloat(), "디저트"))

        val pieDataSet = PieDataSet(pieEntries, "")
        pieDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

        val pieData = PieData(pieDataSet)
        pieData.setValueTextSize(14f)

        binding.piechartSales.data = pieData
        binding.piechartSales.animateXY(3000, 3000)
    }

    private fun setDataBarChart(salesList: List<ProductSalesDto>) {
        val productNames = salesList.map { it.name }

        val barEntries = mutableListOf<BarEntry>()
        salesList.forEachIndexed { index, product ->
            barEntries.add(BarEntry((index + 1).toFloat(), product.sales.toFloat()))
        }

        val barDataSet = BarDataSet(barEntries, "")
        barDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

        val barData = BarData(barDataSet)
        barData.setValueTextSize(14f)

        binding.barchartSales.apply {
            xAxis.valueFormatter = MyXAxisFormatter(productNames)
            data = barData
            setVisibleXRangeMaximum(7f)
            animateY(3000) // 밑에서부터 올라오는 애니매이션 적용
        }
    }

    private fun otherListeners() {
        binding.refreshSales.setOnRefreshListener {
            initData()
            binding.refreshSales.isRefreshing = false
        }
        binding.barchartSales.setOnTouchListener { v, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    binding.refreshSales.isEnabled = false
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    binding.refreshSales.isEnabled = true
                }
            }
//            v.parent.requestDisallowInterceptTouchEvent(true)
            false
        }
    }

    private fun initPieChart() {
        binding.piechartSales.apply {
            description.isEnabled = false
        }
    }

    private fun initBarChart() {
        binding.barchartSales.apply {
            description.isEnabled = false // description 안보이기
            setPinchZoom(false) // 핀치줌 설정 안함
            setDrawGridBackground(false) //격자구조 넣을건지
            axisRight.isEnabled = false // 오른쪽 Y축을 안보이게 해줌.
            setTouchEnabled(true) // 그래프 터치해도 아무 변화없게 막음

            axisLeft.run {
                setDrawGridLines(false) //격자 라인 활용
                setDrawAxisLine(false) // 축 그리기 설정
            }
            xAxis.run {
                position = XAxis.XAxisPosition.BOTTOM//X축을 아래에다가 둔다.
                setDrawGridLines(false) // 격자
                granularity = 1f
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class MyXAxisFormatter(private val productNames : List<String>) : ValueFormatter(){
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return productNames[value.toInt() - 1]
        }
    }
}