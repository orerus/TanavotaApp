package com.tanavota.tanavota.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tanavota.tanavota.R
import com.tanavota.tanavota.databinding.FragmentHomeBinding
import com.tanavota.tanavota.view.BaseFragment
import com.tanavota.tanavota.viewmodel.home.HomeViewModel

/**
 * Created by murata_sho on 2018/03/26.
 */
class HomeFragment : BaseFragment(), HomeViewModel.Delegate {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
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

        val binding = FragmentHomeBinding.bind(view)
        binding.viewModel = viewModel
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
    }

    // end region

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}