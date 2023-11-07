package alex.lop.io.alexProject.fragment

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.adapters.CreatorAdapter
import alex.lop.io.alexProject.databinding.FragmentCreatorBinding
import alex.lop.io.alexProject.state.ResourceState
import alex.lop.io.alexProject.util.hide
import alex.lop.io.alexProject.util.show
import alex.lop.io.alexProject.util.toast
import alex.lop.io.alexProject.viewModel.CreatorViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CreatorFragment : BaseFragment<FragmentCreatorBinding, CreatorViewModel>() {
    override val viewModel : CreatorViewModel by viewModels()
    private val creatorAdapter by lazy { CreatorAdapter() }

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentCreatorBinding =
        FragmentCreatorBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        collectObserver()
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.creatorList.collect {resource ->
            when(resource) {
                is ResourceState.Success -> {
                    binding.progressBarDetail.hide()
                    resource.data?.let { values ->
                        if (values.data.result.isNotEmpty()) {
                            creatorAdapter.creatorList = values.data.result.toList()
                        } else {
                            toast("Lista vazia")
                        }
                    }
                }
                is ResourceState.Error -> {
                    binding.progressBarDetail.hide()
                    resource.message?.let { message->
                        toast(R.string.an_error_occurred.toString())
                        Timber.tag("CreatorFragment").e("Error -> $message")
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
        rvCreatorList.apply {
            adapter = creatorAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

}