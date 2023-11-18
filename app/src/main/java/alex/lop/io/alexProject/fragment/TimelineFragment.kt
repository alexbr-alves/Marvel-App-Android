package alex.lop.io.alexProject.fragment

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.databinding.FragmentSearchCharacterBinding
import alex.lop.io.alexProject.adapters.TimelineAdapter
import alex.lop.io.alexProject.viewModel.SearchCharacterViewModel
import alex.lop.io.alexProject.state.ResourceState
import alex.lop.io.alexProject.util.Constants.DEFAULT_QUERY
import alex.lop.io.alexProject.util.Constants.LAST_SEARCH_QUERY
import alex.lop.io.alexProject.util.setInvisible
import alex.lop.io.alexProject.util.setVisible
import alex.lop.io.alexProject.util.toast
import alex.lop.io.alexProject.viewModel.TimelineViewModel
import android.os.Bundle
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class TimelineFragment :
    BaseFragment<FragmentSearchCharacterBinding, TimelineViewModel>() {
    override val viewModel : TimelineViewModel by viewModels()
    private val timelineAdapter by lazy { TimelineAdapter() }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        collectObserve()
    }

    private fun collectObserve() = lifecycleScope.launch {
        viewModel.listComic.collect { result ->
            when (result) {
                is ResourceState.Success -> {
                    binding.progressbarSearch.setInvisible()
                    result.data?.let {
                        if (it.data.result.isNotEmpty()) {
                            timelineAdapter.timelineList = it.data.result.toList()
                        }
                    }
                }

                is ResourceState.Error -> {
                    binding.progressbarSearch.setInvisible()
                    result.message?.let { message ->
                        toast(getString(R.string.an_error_occurred))
                        Timber.tag("TimelineFragment").e("Error -> $message")
                    }
                }

                is ResourceState.Loading -> {
                    binding.progressbarSearch.setVisible()
                }

                else -> {}
            }
        }

    }

    private fun setupRecycleView() = with(binding) {
        recycleView.apply {
            adapter = timelineAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentSearchCharacterBinding =
        FragmentSearchCharacterBinding.inflate(inflater, container, false)
}