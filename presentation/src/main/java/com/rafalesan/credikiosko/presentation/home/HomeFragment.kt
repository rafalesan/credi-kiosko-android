package com.rafalesan.credikiosko.presentation.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.base.BaseViewModelFragment
import com.rafalesan.credikiosko.presentation.base.utils.AutoClearedValue
import com.rafalesan.credikiosko.presentation.databinding.FrgHomeBinding
import com.rafalesan.credikiosko.presentation.utils.ext.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseViewModelFragment<HomeViewModel, FrgHomeBinding>() {

    override val contentViewLayoutId = R.layout.frg_home
    override val viewModel: HomeViewModel by viewModel()

    private var homeOptionsAdapter by AutoClearedValue<HomeOptionsAdapter>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    override fun onSubscribeToViewModel() {
        super.onSubscribeToViewModel()
        viewModel.homeOptions.collect(viewLifecycleOwner) {
            showHomeOptions(it)
        }
    }

    private fun setup() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        homeOptionsAdapter = HomeOptionsAdapter {
            viewModel.perform(HomeAction.HomeOptionSelected(it))
        }
        binding.rvHomeMenu.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvHomeMenu.adapter = homeOptionsAdapter
        binding.rvHomeMenu.addItemDecoration(GridSpacingItemDecoration(2, resources.getDimensionPixelSize(R.dimen.space_2x), true, 0))
    }

    private fun showHomeOptions(items: List<HomeOption>?) {
        homeOptionsAdapter.submitList(items)
    }

}
