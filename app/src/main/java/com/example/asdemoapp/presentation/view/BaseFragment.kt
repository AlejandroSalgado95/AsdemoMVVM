package com.example.asdemoapp.presentation.view

import android.net.Uri
import android.view.View
import android.widget.ProgressBar
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asdemoapp.databinding.ViewErrorBinding
import com.example.asdemoapp.databinding.ViewNothingFoundBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    protected fun showErrorUI(
        loading: ProgressBar,
        rv: RecyclerView,
        errorContainer: ViewErrorBinding,
        nothingFoundContainer: ViewNothingFoundBinding
    ) {
        errorContainer.container.visibility = View.VISIBLE
        rv.visibility = View.GONE
        loading.visibility = View.GONE
        nothingFoundContainer.container.visibility = View.GONE
    }

    protected fun showLoading(
        loading: ProgressBar,
        rv: RecyclerView,
        errorContainer: ViewErrorBinding,
        nothingFoundContainer: ViewNothingFoundBinding
    ) {
        loading.visibility = View.VISIBLE
        rv.visibility = View.GONE
        errorContainer.container.visibility = View.GONE
        nothingFoundContainer.container.visibility = View.GONE
    }

    protected fun showList(
        loading: ProgressBar,
        rv: RecyclerView,
        errorContainer: ViewErrorBinding,
        nothingFoundContainer: ViewNothingFoundBinding
    ) {
        rv.visibility = View.VISIBLE
        loading.visibility = View.GONE
        errorContainer.container.visibility = View.GONE
        nothingFoundContainer.container.visibility = View.GONE
    }

    protected fun showNothingFound(
        loading: ProgressBar,
        rv: RecyclerView,
        errorContainer: ViewErrorBinding,
        nothingFoundContainer: ViewNothingFoundBinding
    ) {
        nothingFoundContainer.container.visibility = View.VISIBLE
        rv.visibility = View.GONE
        loading.visibility = View.GONE
        errorContainer.container.visibility = View.GONE
    }

    protected fun setupRecyclerView(rv: RecyclerView) {
        val dividerItemDecoration = DividerItemDecoration(
            rv.context,
            LinearLayoutManager.VERTICAL
        )
        rv.addItemDecoration(dividerItemDecoration)
        rv.setHasFixedSize(true)
        rv.itemAnimator = null
        rv.layoutManager = LinearLayoutManager(requireContext())
    }

    protected fun onOpenUrlLinkClicked(url: String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }

}