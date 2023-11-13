package alex.lop.io.alexProject.fragment.detailsComic

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.adapters.CreatorDetailsAdapter
import alex.lop.io.alexProject.databinding.FragmentCreatorComicBinding
import alex.lop.io.alexProject.fragment.BaseFragment
import alex.lop.io.alexProject.state.ResourceState
import alex.lop.io.alexProject.util.setInvisible
import alex.lop.io.alexProject.util.setVisible
import alex.lop.io.alexProject.util.toast
import alex.lop.io.alexProject.viewModel.detailsComics.CreatorComicViewModel
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
class CreatorComicFragment(private val comicId: Int) :
    BaseFragment<FragmentCreatorComicBinding, CreatorComicViewModel>(){
    override val viewModel : CreatorComicViewModel by viewModels()
    private val creatorDetailsAdapter by lazy { CreatorDetailsAdapter() }

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentCreatorComicBinding = FragmentCreatorComicBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetch(comicId)
        setupRecyclerView()
        collectObserver()
    }

    private fun setupRecyclerView() = with(binding) {
        rvCreatorCharacter.apply {
            adapter = creatorDetailsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.list.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    binding.progressBarDetail.setInvisible()
                    resource.data?.let { values ->
                        if (values.data.result.isEmpty()) {
                            binding.textEmpty.setVisible()
                        } else {
                            creatorDetailsAdapter.creators = values.data.result.toList()
                        }
                    }
                }

                is ResourceState.Error -> {
                    binding.progressBarDetail.setInvisible()
                    resource.message?.let { message ->
                        toast(getString(R.string.an_error_occurred))
                        Timber.tag("CreatorComicFragment").e("Error -> $message")
                    }
                }

                is ResourceState.Loading -> {
                    binding.progressBarDetail.setVisible()
                }

                else -> {
                }
            }
        }
    }

}