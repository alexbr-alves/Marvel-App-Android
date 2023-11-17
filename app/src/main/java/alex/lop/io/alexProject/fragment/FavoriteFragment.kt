package alex.lop.io.alexProject.fragment

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.databinding.FragmentFavoriteCharacterBinding
import alex.lop.io.alexProject.adapters.FavoriteAdapter
import alex.lop.io.alexProject.data.model.ThumbnailModel
import alex.lop.io.alexProject.data.model.character.CharacterModel
import alex.lop.io.alexProject.data.model.comic.ComicModel
import alex.lop.io.alexProject.data.model.event.EventModel
import alex.lop.io.alexProject.viewModel.FavoriteCharacterViewModel
import alex.lop.io.alexProject.state.ResourceState
import alex.lop.io.alexProject.util.Constants
import alex.lop.io.alexProject.util.setInvisible
import alex.lop.io.alexProject.util.setVisible
import alex.lop.io.alexProject.util.toast
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment :
    BaseFragment<FragmentFavoriteCharacterBinding, FavoriteCharacterViewModel>() {
    override val viewModel : FavoriteCharacterViewModel by viewModels()

    private val favoriteAdapter by lazy { FavoriteAdapter() }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        clickAdapter()
        observer()
    }

    private fun observer() = lifecycleScope.launch {
        viewModel.favorites.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    resource.data?.let {
                        binding.tvEmptyList.setInvisible()
                        favoriteAdapter.favorites = it.toList()
                    }
                }

                is ResourceState.Empty -> {
                    binding.tvEmptyList.setVisible()
                }

                else -> {}
            }
        }
    }

    private fun clickAdapter() {
        favoriteAdapter.setOnClickListener { favoriteModel ->
            if (favoriteModel.type == Constants.CHARACTER) {
                val action = FavoriteFragmentDirections
                    .actionFavoriteCharacterFragmentToDetailsCharacterFragment(
                        CharacterModel(
                            favoriteModel.id,
                            favoriteModel.title,
                            favoriteModel.description,
                            ThumbnailModel(
                                favoriteModel.thumbnailModel.path,
                                favoriteModel.thumbnailModel.extension
                            )
                        )
                    )
                findNavController().navigate(action)
            }
            else if (favoriteModel.type == Constants.COMIC) {
                val action = FavoriteFragmentDirections
                    .actionFavoriteCharacterFragmentToDetailsComicFragment(
                        ComicModel(
                            favoriteModel.id,
                            favoriteModel.title,
                            favoriteModel.description,
                            ThumbnailModel(
                                favoriteModel.thumbnailModel.path,
                                favoriteModel.thumbnailModel.extension
                            )
                        )
                    )
                findNavController().navigate(action)
            } else if (favoriteModel.type == Constants.EVENT) {
                val action = FavoriteFragmentDirections
                    .actionFavoriteCharacterFragmentToDetailsEventFragment(
                        EventModel(
                            favoriteModel.id,
                            favoriteModel.title,
                            favoriteModel.description,
                            ThumbnailModel(
                                favoriteModel.thumbnailModel.path,
                                favoriteModel.thumbnailModel.extension
                            )
                        )
                    )
                findNavController().navigate(action)
            }
        }

    }

    private fun setupRecycleView() = with(binding) {
        rvFavoriteCharacter.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(context)
        }
        ItemTouchHelper(itemTouchHelperCallBack()).attachToRecyclerView(rvFavoriteCharacter)
    }

    private fun itemTouchHelperCallBack() : ItemTouchHelper.Callback {
        return object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView : RecyclerView,
                viewHolder : RecyclerView.ViewHolder,
                target : RecyclerView.ViewHolder
            ) : Boolean {
                return false
            }

            override fun onSwiped(viewHolder : RecyclerView.ViewHolder, direction : Int) {
                val character = favoriteAdapter.getCharacterPosition(viewHolder.adapterPosition)
                viewModel.delete(character).also {
                    toast(getString(R.string.message_delete_character))
                }
            }

        }
    }

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentFavoriteCharacterBinding =
        FragmentFavoriteCharacterBinding.inflate(inflater, container, false)
}