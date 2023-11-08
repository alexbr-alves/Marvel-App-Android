package alex.lop.io.alexProject.fragment

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.adapters.EventAdapter
import alex.lop.io.alexProject.databinding.FragmentEventBinding
import alex.lop.io.alexProject.state.ResourceState
import alex.lop.io.alexProject.util.hide
import alex.lop.io.alexProject.util.show
import alex.lop.io.alexProject.util.toast
import alex.lop.io.alexProject.viewModel.EventViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class EventFragment : BaseFragment<FragmentEventBinding,EventViewModel>(){
    override val viewModel : EventViewModel by viewModels()
    private val eventAdapter by lazy { EventAdapter() }

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentEventBinding =
        FragmentEventBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectObserver()
        setupRecycleView()
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.eventList.collect { resource ->
            when(resource) {
                is ResourceState.Success -> {
                    binding.progressBar.hide()
                    resource.data?.let { values ->
                        if (values.data.result.isNotEmpty()) {
                            eventAdapter.eventList = values.data.result.toList()
                        } else {
                            toast("Lista vazia")
                        }
                    }
                }
                is ResourceState.Error -> {
                    binding.progressBar.hide()
                    resource.message?.let { message ->
                        toast(getString(R.string.an_error_occurred))
                        Timber.tag("EventFragment").e("Error -> $message")
                    }
                }
                is ResourceState.Loading -> {
                    binding.progressBar.show()
                }
                else -> {}
            }
        }
    }

    private fun setupRecycleView() = with(binding) {
        rvEventList.apply {
            adapter = eventAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

}