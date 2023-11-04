package alex.lop.io.alexProject.ui.search

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.databinding.FragmentSearchCharacterBinding
import alex.lop.io.alexProject.ui.adapters.CharacterAdapter
import alex.lop.io.alexProject.ui.base.BaseFragment
import alex.lop.io.alexProject.ui.state.ResourceState
import alex.lop.io.alexProject.util.Constants.DEFAULT_QUERY
import alex.lop.io.alexProject.util.Constants.LAST_SEARCH_QUERY
import alex.lop.io.alexProject.util.hide
import alex.lop.io.alexProject.util.show
import alex.lop.io.alexProject.util.toast
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
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchCharacterFragment :
    BaseFragment<FragmentSearchCharacterBinding, SearchCharacterViewModel>() {
    override val viewModel : SearchCharacterViewModel by viewModels()
    private val characterAdapter by lazy { CharacterAdapter() }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        clickAdapter()

        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        searchInit(query)
        collectObserve()
    }

    private fun collectObserve() = lifecycleScope.launch {
        viewModel.searchCharacter.collect { result ->
            when (result) {
                is ResourceState.Success -> {
                    binding.progressbarSearch.hide()
                    result.data?.let {
                        characterAdapter.characters = it.data.results.toList()
                    }
                }
                is ResourceState.Error -> {
                    binding.progressbarSearch.hide()
                    result.message?.let { message ->
                        toast(getString(R.string.an_error_occurred))
                        Timber.tag("SearchCharacterFragment").e("Error -> $message")
                    }
                }
                is ResourceState.Loading -> {
                    binding.progressbarSearch.show()
                }
                else -> {}
            }
        }
    }

    private fun searchInit(query : String) = with(binding) {
        edSearchCharacter.setText(query)
        edSearchCharacter.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateCharacterList()
                true
            } else {
                false
            }
        }
        edSearchCharacter.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action == ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                updateCharacterList()
                true
            } else {
                false
            }
        }
    }

    private fun updateCharacterList() = with(binding) {
        edSearchCharacter.editableText.trim().let {
            if (it.isNotEmpty()) {
                searchQuery(it.toString())
            }
        }
    }

    private fun searchQuery(query : String) {
        viewModel.fetch(query)
    }

    override fun onSaveInstanceState(outState : Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY,
            binding.edSearchCharacter.editableText.trim().toString())
    }

    private fun clickAdapter() {
        characterAdapter.setOnClickListener { characterModel ->
            val action = SearchCharacterFragmentDirections.actionSearchCharacterFragmentToDetailsCharacterFragment(characterModel)
            findNavController().navigate(action)
        }
    }

    private fun setupRecycleView() = with(binding) {
        rvSearchCharacter.apply {
            adapter = characterAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentSearchCharacterBinding =
        FragmentSearchCharacterBinding.inflate(inflater, container, false)
}