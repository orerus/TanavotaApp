package com.tanavota.tanavota.view.home

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tanavota.tanavota.R
import com.tanavota.tanavota.databinding.FragmentHomeBinding
import com.tanavota.tanavota.view.BaseFragment
import com.tanavota.tanavota.view.home.epoxy.HomeEpoxyController
import com.tanavota.tanavota.viewmodel.home.HomeViewModel

/**
 * Created by murata_sho on 2018/03/26.
 */
class HomeFragment : BaseFragment(), HomeViewModel.Delegate {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var controller: HomeEpoxyController
    private var shouldInitialLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = HomeViewModel(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = HomeEpoxyController()
        val dividerItemDecoration =
                DividerItemDecoration(this.context, LinearLayoutManager(this.activity).orientation)
        this.context
                ?.let { ContextCompat.getDrawable(it, R.drawable.home_divider) }
                ?.let { dividerItemDecoration.setDrawable(it) }

        val binding = FragmentHomeBinding.bind(view)
        binding.viewModel = viewModel
        binding.recyclerView.adapter = controller.adapter
        binding.recyclerView.addOnScrollListener(viewModel.scrollListener)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        this.controller = controller
        this.binding = binding
    }

    override fun onResume() {
        super.onResume()

        if (shouldInitialLoad) {
            viewModel.load()
        }
    }

    // region HomeViewModel.Delegate implementation
    override fun onInitialLoaded() {
        shouldInitialLoad = false
        controller.setData(viewModel)
    }

    override fun onDataLoaded() {
        controller.setData(viewModel)
    }
    // end region

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}