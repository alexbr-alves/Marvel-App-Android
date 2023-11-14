package alex.lop.io.alexProject.fragment

import alex.lop.io.alexProject.adapters.CharacterDetailsAdapter
import alex.lop.io.alexProject.data.model.character.CharacterModel
import alex.lop.io.alexProject.data.model.serie.SeriesModel
import alex.lop.io.alexProject.databinding.FragmentHomeBinding
import alex.lop.io.alexProject.state.ResourceState
import alex.lop.io.alexProject.util.loadImage
import alex.lop.io.alexProject.viewModel.HomeViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel : HomeViewModel by viewModels()

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickEvents()
    }

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    private fun clickEvents() = with(binding) {
        textCharacterMore.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToListCharacterFragment()
            findNavController().navigate(action)
        }
        textComicMore.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToComicsFragment()
            findNavController().navigate(action)
        }
        textEventMore.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEventFragment2()
            findNavController().navigate(action)
        }
    }
}