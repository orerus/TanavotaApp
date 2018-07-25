package com.tanavota.tanavota.view.history

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tanavota.tanavota.R
import com.tanavota.tanavota.databinding.FragmentHistoryBinding
import com.tanavota.tanavota.extension.getNullable
import com.tanavota.tanavota.model.domain.favorite.FavoriteOperationEvent
import com.tanavota.tanavota.view.BaseFragment
import com.tanavota.tanavota.view.Navigator
import com.tanavota.tanavota.view.articledetail.ArticleDetailFragment
import com.tanavota.tanavota.view.home.epoxy.HomeEpoxyController
import com.tanavota.tanavota.viewmodel.history.HistoryViewModel

/**
 * Created by murata_sho on 2018/03/26.
 */
class HistoryFragment : BaseFragment(), HistoryViewModel.Delegate, Navigator {
    private lateinit var viewModel: HistoryViewModel
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var controller: HomeEpoxyController
    private var shouldInitialLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = HistoryViewModel(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = HomeEpoxyController()
        val dividerItemDecoration =
                DividerItemDecoration(this.context, LinearLayoutManager(this.activity).orientation)
        this.context
                ?.let { ContextCompat.getDrawable(it, R.drawable.home_divider) }
                ?.let { dividerItemDecoration.setDrawable(it) }

        val binding = FragmentHistoryBinding.bind(view)
        binding.viewModel = viewModel
        binding.recyclerView.adapter = controller.adapter
        binding.recyclerView.addOnScrollListener(viewModel.scrollListener)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        binding.swipeRefresh.setOnRefreshListener {
            shouldInitialLoad = true
            resumeAction()
        }
        this.controller = controller
        this.binding = binding

        setTitle()
    }

    override fun onResume() {
        super.onResume()

        resumeAction()
    }

    private fun resumeAction() {
        if (shouldInitialLoad) {
            viewModel.load()
        }
    }

    override fun onPause() {
        super.onPause()

        viewModel.dispose()
    }

    override fun setTitle() {
        wHeaderContents.getNullable()?.setHeaderTitle(R.string.nav_history)
    }

    override fun onToast(messageId: Int) {
        Toast.makeText(this.context, messageId, Toast.LENGTH_SHORT).show()
    }

    override fun onFavoriteOperationEvent(event: FavoriteOperationEvent) {
        super.onFavoriteOperationEvent(event)

        viewModel.takeIn(event)
    }

    // region HistoryViewModel.Delegate implementation
    override fun onInitialLoaded() {
        shouldInitialLoad = false
        updateController()
        if (binding.swipeRefresh.isRefreshing) {
            binding.swipeRefresh.isRefreshing = false
        }
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
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }
}