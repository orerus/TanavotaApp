package com.tanavota.tanavota.view.home

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tanavota.tanavota.R
import com.tanavota.tanavota.databinding.FragmentHomeBinding
import com.tanavota.tanavota.extension.getNullable
import com.tanavota.tanavota.model.domain.favorite.FavoriteOperationEvent
import com.tanavota.tanavota.view.BaseFragment
import com.tanavota.tanavota.view.Navigator
import com.tanavota.tanavota.view.articledetail.ArticleDetailFragment
import com.tanavota.tanavota.view.home.epoxy.HomeEpoxyController
import com.tanavota.tanavota.viewmodel.home.HomeViewModel

/**
 * Created by murata_sho on 2018/03/26.
 */
class HomeFragment : BaseFragment(), HomeViewModel.Delegate, Navigator {
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

        setTitle()
    }

    override fun onResume() {
        super.onResume()

        if (shouldInitialLoad) {
            viewModel.load()
        }
    }

    override fun onPause() {
        super.onPause()

        viewModel.dispose()
    }

    override fun setTitle() {
        wHeaderContents.getNullable()?.setHeaderTitle(R.string.app_name)
    }

    override fun onToast(messageId: Int) {
        Toast.makeText(this.context, messageId, Toast.LENGTH_SHORT).show()
    }

    override fun onFavoriteOperationEvent(event: FavoriteOperationEvent) {
        super.onFavoriteOperationEvent(event)

        viewModel.takeIn(event)
    }

    // region HomeViewModel.Delegate implementation
    override fun onInitialLoaded() {
        shouldInitialLoad = false
        updateController()
    }

    override fun onDataLoaded() {
        updateController()
    }

    private fun updateController() {
        controller.setData(viewModel, viewModel)
    }

    override fun onNavigateToDetail(id: String) {
        val fragment = ArticleDetailFragment.newInstance(id)
        fragmentManager?.let { navigateToFragment(it, fragment) }
    }
    // end region

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}