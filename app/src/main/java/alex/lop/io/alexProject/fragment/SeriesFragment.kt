package alex.lop.io.alexProject.fragment


import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.adapters.SeriesAdapter
import alex.lop.io.alexProject.databinding.FragmentSeriesBinding
import alex.lop.io.alexProject.state.ResourceState
import alex.lop.io.alexProject.util.hide
import alex.lop.io.alexProject.util.show
import alex.lop.io.alexProject.util.toast
import alex.lop.io.alexProject.viewModel.SeriesViewModel
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
class SeriesFragment : BaseFragment<FragmentSeriesBinding, SeriesViewModel>() {
    override val viewModel : SeriesViewModel by viewModels()
    private val seriesAdapter by lazy { SeriesAdapter() }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        collectObserve()
        searchInit()
    }

    private fun setupRecycleView() = with(binding) {
        recycleView.apply {
            adapter = seriesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun collectObserve() = lifecycleScope.launch {
        viewModel.seriesList.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    resource.data?.let { values ->
                        binding.progressBar.hide()
                        seriesAdapter.seriesList = values.data.result.toList()
                    }
                }

                is ResourceState.Error -> {
                    binding.progressBar.hide()
                    resource.message?.let { message ->
                        toast(getString(R.string.an_error_occurred))
                        Timber.tag("ListCharacterFragment").e("Error -> $message")
                    }
                }

                is ResourceState.Loading -> {
                    binding.progressBar.show()
                }

                else -> {}
            }
        }
    }

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentSeriesBinding =
        FragmentSeriesBinding.inflate(inflater, container, false)

    private fun searchInit(query : String? = null) = with(binding) {
        editTextSearch.setText(query)
        editTextSearch.addTextChangedListener(object : TextWatcher {
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