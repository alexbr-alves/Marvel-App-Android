package alex.lop.io.alexProject.fragment.detailsEvent


import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.adapters.CharacterDetailsAdapter
import alex.lop.io.alexProject.databinding.FragmentCharacterComicBinding
import alex.lop.io.alexProject.databinding.FragmentCharacterEventBinding
import alex.lop.io.alexProject.fragment.BaseFragment
import alex.lop.io.alexProject.state.ResourceState
import alex.lop.io.alexProject.util.setInvisible
import alex.lop.io.alexProject.util.setVisible
import alex.lop.io.alexProject.util.toast
import alex.lop.io.alexProject.viewModel.detailEvent.CharacterEventViewModel
import alex.lop.io.alexProject.viewModel.detailsComics.CharactersComicViewModel
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
class CharactersEventFragment(private val comicId: Int) :
    BaseFragment<FragmentCharacterEventBinding, CharacterEventViewModel>() {

    override val viewModel : CharacterEventViewModel by viewModels()
    private val characterDetailsAdapter by lazy { CharacterDetailsAdapter() }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCharacterEventBinding =
        FragmentCharacterEventBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetch(comicId)
        setupRecyclerView()
        collectObserver()
    }

    private fun setupRecyclerView() = with(binding) {
        rvComics.apply {
            adapter = characterDetailsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.list.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    binding.progressBarDetail.setInvisible()
                    resource.data?.let { values ->
                        if (values.data.results.isEmpty()) {
                            binding.textEmpty.setVisible()
                        } else {
                            characterDetailsAdapter.characters = values.data.results.toList()
                        }
                    }
                }

                is ResourceState.Error -> {
                    binding.progressBarDetail.setInvisible()
                    resource.message?.let { message ->
                        toast(getString(R.string.an_error_occurred))
                        Timber.tag("CharactersComicFragment").e("Error -> $message")
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
