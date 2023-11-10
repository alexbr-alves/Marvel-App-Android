package alex.lop.io.alexProject.fragment

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.adapters.StoriesAdapter
import alex.lop.io.alexProject.databinding.FragmentStoriesBinding
import alex.lop.io.alexProject.state.ResourceState
import alex.lop.io.alexProject.util.setInvisible
import alex.lop.io.alexProject.util.setVisible
import alex.lop.io.alexProject.util.toast
import alex.lop.io.alexProject.viewModel.StoriesViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class StoriesFragment : BaseFragment<FragmentStoriesBinding, StoriesViewModel>() {
    override val viewModel : StoriesViewModel by viewModels()
    private val storiesAdapter by lazy { StoriesAdapter() }

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentStoriesBinding = FragmentStoriesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectObserver()
        setupRecycleView()
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.storiesList.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    binding.progressBar.setInvisible()
                    resource.data?.let { values ->
                        if (values.data.result.isNotEmpty()) {
                            storiesAdapter.storiesList = values.data.result.toList()
                        } else {
                            toast("Lista vazia")
                        }
                    }
                }

                is ResourceState.Error -> {
                    binding.progressBar.setInvisible()
                    resource.message?.let { message ->
                        toast(getString(R.string.an_error_occurred))
                        Timber.tag("EventFragment").e("Error -> $message")
                    }
                }

                is ResourceState.Loading -> {
                    binding.progressBar.setVisible()
                }

                else -> {}
            }
        }
    }

    private fun setupRecycleView() = with(binding) {
        recycleView.apply {
            adapter = storiesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

}