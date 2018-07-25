package com.tanavota.tanavota.view.articledetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tanavota.tanavota.R
import com.tanavota.tanavota.databinding.FragmentArticleDetailBinding
import com.tanavota.tanavota.di.ApplicationComponentStore
import com.tanavota.tanavota.extension.getNullable
import com.tanavota.tanavota.view.BaseFragment
import com.tanavota.tanavota.view.articledetail.epoxy.ArticleDetailEpoxyController
import com.tanavota.tanavota.viewmodel.articledetail.ArticleDetailViewModel
import javax.inject.Inject

class ArticleDetailFragment : BaseFragment(), ArticleDetailViewModel.Delegate {
    @Inject
    lateinit var viewModel: ArticleDetailViewModel
    private val controller = ArticleDetailEpoxyController()
    private lateinit var binding: FragmentArticleDetailBinding
    private var shouldInitialLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ApplicationComponentStore.get().activityComponent().inject(this)

        viewModel.setDelegate(this)

        if (savedInstanceState == null) {
            viewModel.articleId = arguments?.getString(ARTICLE_ID_KEY) ?: ""
        } else {
            viewModel.restoreSavedInstanceState(savedInstanceState)
            shouldInitialLoad = savedInstanceState.getBoolean(SHOULD_INITIAL_LOAD_KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_article_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArticleDetailBinding.bind(view)
        binding.viewModel = viewModel
        binding.operator = viewModel
        
        binding.recyclerView.adapter = controller.adapter
        this.binding = binding

        setTitle()
    }

    override fun onResume() {
        super.onResume()

        if (shouldInitialLoad) {
            viewModel.load()
        } else {
            viewModel.favoriteLoad()
        }
    }

    override fun onPause() {
        super.onPause()

        viewModel.dispose()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putAll(viewModel.createSaveInstanceState())
        outState.putBoolean(SHOULD_INITIAL_LOAD_KEY, shouldInitialLoad)
    }

    override fun setTitle() {
        wHeaderContents.getNullable()?.setHeaderTitle(R.string.nav_article_detail)
    }

    override fun onToast(messageId: Int) {
        Toast.makeText(this.context, messageId, Toast.LENGTH_SHORT).show()
    }

    // region ArticleDetailViewModel.Delegate implementation
    override fun onInitialLoaded() {
        shouldInitialLoad = false
        updateController()
    }

    override fun onFavoriteLoaded() {
        updateController()
    }

    override fun onTransfer(intent: Intent) {
        startActivity(intent)
    }
    // end region

    private fun updateController() {
        controller.setData(viewModel)
    }

    companion object {
        const val ARTICLE_ID_KEY = "ARTICLE_ID_KEY"
        const val ARTICLE_KEY = "ARTICLE_KEY"
        const val IMAGES_KEY = "IMAGES_KEY"
        const val SHOULD_INITIAL_LOAD_KEY = "SHOULD_INITIAL_LOAD_KEY"

        fun newInstance(id: String): ArticleDetailFragment {
            val arguments = Bundle().apply {
                putString(ARTICLE_ID_KEY, id)
            }
            return ArticleDetailFragment().apply {
                this.arguments = arguments
            }
        }
    }
}