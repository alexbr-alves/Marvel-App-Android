package alex.lop.io.alexProject.fragment.detailsCharacter

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.adapters.StoriesDetailsAdapter
import alex.lop.io.alexProject.databinding.FragmentStoriesCharapterBinding
import alex.lop.io.alexProject.fragment.BaseFragment
import alex.lop.io.alexProject.state.ResourceState
import alex.lop.io.alexProject.util.setInvisible
import alex.lop.io.alexProject.util.setVisible
import alex.lop.io.alexProject.util.toast
import alex.lop.io.alexProject.viewModel.detailCharacter.StoriesCharacterViewModel
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
class StoriesCharacterFragment(private val characterId: Int) :
    BaseFragment<FragmentStoriesCharapterBinding, StoriesCharacterViewModel>() {

    private val storiesDetailsAdapter by lazy { StoriesDetailsAdapter() }
    override val viewModel: StoriesCharacterViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStoriesCharapterBinding =
        FragmentStoriesCharapterBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchStory(characterId)
        setupRecyclerView()
        collectObserver()
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.storyList.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    binding.progressBarDetail.setInvisible()
                    resource.data?.let { values ->
                        if (values.data.result.isNotEmpty()) {
                            storiesDetailsAdapter.stories = values.data.result.toList()
                        } else {
                            binding.textEmpty.setVisible()
                        }
                    }
                }

                is ResourceState.Error -> {
                    binding.progressBarDetail.setInvisible()
                    resource.message?.let { message ->
                        toast(getString(R.string.an_error_occurred))
                        Timber.tag("ListCharacterFragment").e("Error -> $message")
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

    private fun setupRecyclerView() = with(binding) {
        rvStory.apply {
            adapter = storiesDetailsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}
