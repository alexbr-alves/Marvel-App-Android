package alex.lop.io.alexProject.fragment

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.adapters.CharacterAdapter
import alex.lop.io.alexProject.databinding.FragmentCharacterBinding
import alex.lop.io.alexProject.viewModel.ListCharacterViewModel
import alex.lop.io.alexProject.state.ResourceState
import alex.lop.io.alexProject.util.setInvisible
import alex.lop.io.alexProject.util.setVisible
import alex.lop.io.alexProject.util.toast
import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CharacterFragment : BaseFragment<FragmentCharacterBinding, ListCharacterViewModel>() {
    override val viewModel : ListCharacterViewModel by viewModels()
    private val characterAdapter by lazy { CharacterAdapter() }
    private var isSearchExpanded = false

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentCharacterBinding =
        FragmentCharacterBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        clickAdapter()
        collectObserve()
        searchInit()
        handleSearchAnimation()
    }

    private fun handleSearchAnimation() = binding.run {
        searchIcon.setOnClickListener {
            if (isSearchExpanded) {
                collapseSearchBar()
            } else {
                expandSearchBar()
            }
        }
    }

    private fun expandSearchBar() = binding.run {
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addUpdateListener { valueAnimator ->
            val fraction = valueAnimator.animatedValue as Float
            val params = searchEditText.layoutParams as ConstraintLayout.LayoutParams
            params.matchConstraintPercentWidth = fraction
            searchEditText.layoutParams = params
        }
        animator.duration = 300
        animator.start()

        searchEditText.visibility = View.VISIBLE
        searchEditText.requestFocus()

        isSearchExpanded = true
    }

    private fun collapseSearchBar() = binding.run {
        val animator = ValueAnimator.ofFloat(1f, 0f)
        animator.addUpdateListener { valueAnimator ->
            val fraction = valueAnimator.animatedValue as Float
            val params = searchEditText.layoutParams as ConstraintLayout.LayoutParams
            params.matchConstraintPercentWidth = fraction
            searchEditText.layoutParams = params
        }
        animator.duration = 300
        animator.start()

        searchEditText.visibility = View.GONE
        isSearchExpanded = false
    }

    private fun collectObserve() = lifecycleScope.launch {
        viewModel.list.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    resource.data?.let { values ->
                        binding.progressCircular.setInvisible()
                        characterAdapter.characters = values.data.results.toList()
                    }
                }

                is ResourceState.Error -> {
                    binding.progressCircular.setInvisible()
                    resource.message?.let { message ->
                        toast(getString(R.string.an_error_occurred))
                        Timber.tag("ListCharacterFragment").e("Error -> $message")
                    }
                }

                is ResourceState.Loading -> {
                    binding.progressCircular.setVisible()
                }

                else -> {}
            }
        }
    }

    private fun clickAdapter() {
        characterAdapter.setOnClickListener { characterModel ->
            val action = CharacterFragmentDirections
                .actionListCharacterFragmentToDetailsCharacterFragment(characterModel)
            findNavController().navigate(action)
        }
    }

    private fun setupRecycleView() = with(binding) {
        rvCharacters.apply {
            adapter = characterAdapter
            layoutManager = LinearLayoutManager(requireContext())
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun searchInit(query : String? = null) = with(binding) {
        searchEditText.setText(query)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                char : CharSequence?,
                start : Int,
                count : Int,
                after : Int
            ) {
            }

            override fun onTextChanged(
                char : CharSequence?,
                start : Int,
                before : Int,
                count : Int
            ) {
                if (char.toString().isEmpty()) {
                    updateCharacterList()
                } else {
                    updateCharacterList(char.toString())
                }
            }

            override fun afterTextChanged(s : Editable?) {}
        })
    }

    private fun updateCharacterList(query : String? = null) {
        viewModel.fetch(query)
    }

}