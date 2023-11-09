package alex.lop.io.alexProject.fragment

import alex.lop.io.alexProject.databinding.FragmentHomeBinding
import alex.lop.io.alexProject.viewModel.HomeViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

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
        menuCharacter.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToListCharacterFragment()
            findNavController().navigate(action)
        }
        menuComic.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToComicsFragment()
            findNavController().navigate(action)
        }
        menuCreator.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToCreatorFragment()
            findNavController().navigate(action)
        }
        menuEvent.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEventFragment2()
            findNavController().navigate(action)
        }
        menuSeries.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSeriesFragment()
            findNavController().navigate(action)
        }
        menuStories.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToStoriesFragment()
            findNavController().navigate(action)
        }
    }
}