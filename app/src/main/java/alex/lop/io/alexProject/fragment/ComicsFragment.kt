package alex.lop.io.alexProject.fragment

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.adapters.ComicsAdapter
import alex.lop.io.alexProject.databinding.FragmentComicBinding
import alex.lop.io.alexProject.state.ResourceState
import alex.lop.io.alexProject.util.hide
import alex.lop.io.alexProject.util.show
import alex.lop.io.alexProject.util.toast
import alex.lop.io.alexProject.viewModel.ComicViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ComicsFragment: BaseFragment<FragmentComicBinding, ComicViewModel>()  {
    override val viewModel : ComicViewModel by viewModels()
    private val comicsAdapter by lazy { ComicsAdapter() }

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentComicBinding =
        FragmentComicBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        collectObserver()
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.comicList.collect { resource ->
            when(resource) {
                is ResourceState.Success -> {
                    binding.progressBarDetail.hide()
                    resource.data?.let { values ->
                        if (values.data.result.isNotEmpty()) {
                            comicsAdapter.comicList = values.data.result.toList()
                        } else  {
                            toast("Lista vazia")
                        }
                    }
                }
                is ResourceState.Error -> {
                    binding.progressBarDetail.hide()
                    resource.message?.let { message ->
                        toast(getString(R.string.an_error_occurred))
                        Timber.tag("ComicsFragment").e("Error -> $message")
                    }
                }
                is ResourceState.Loading -> {
                    binding.progressBarDetail.show()
                }
                else -> {}
            }
        }
    }

    private fun setupRecycleView() = with(binding) {
        rvComicList.apply {
            adapter = comicsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }



}