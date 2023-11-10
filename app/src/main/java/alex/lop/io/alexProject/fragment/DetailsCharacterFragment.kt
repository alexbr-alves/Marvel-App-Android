package alex.lop.io.alexProject.fragment

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.data.model.character.CharacterModel
import alex.lop.io.alexProject.databinding.FragmentDetailsCharacterBinding
import alex.lop.io.alexProject.adapters.ComicCharacterAdapter
import alex.lop.io.alexProject.viewModel.DetailsCharacterViewModel
import alex.lop.io.alexProject.state.ResourceState
import alex.lop.io.alexProject.util.setInvisible
import alex.lop.io.alexProject.util.limitDescription
import alex.lop.io.alexProject.util.loadImage
import alex.lop.io.alexProject.util.setVisible
import alex.lop.io.alexProject.util.toast
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DetailsCharacterFragment :
    BaseFragment<FragmentDetailsCharacterBinding, DetailsCharacterViewModel>() {
    override val viewModel : DetailsCharacterViewModel by viewModels()

    private val args : DetailsCharacterFragmentArgs by navArgs()
    private var comicCharacterAdapter = ComicCharacterAdapter()
    private lateinit var characterModel : CharacterModel

    override fun onCreate(savedInstanceState : Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterModel = args.character
        viewModel.fetch(characterModel.id)
        setupRecycleView()
        onLoadCharacter(characterModel)
        collectObserver()
        descriptionCharacter()
    }

    private fun descriptionCharacter() = binding.run {
        val redColor = resources.getColor(R.color.red)
        val whiteColor = resources.getColor(R.color.white)
        tvDescriptionCharacterDetails.setOnClickListener {
            onShowDialog(characterModel)
        }
        textComic.setOnClickListener {
            textComic.setTextColor(redColor)
            textEvent.setTextColor(whiteColor)
            textSeries.setTextColor(whiteColor)
        }
        textEvent.setOnClickListener {
            textComic.setTextColor(whiteColor)
            textEvent.setTextColor(redColor)
            textSeries.setTextColor(whiteColor)
        }
        textSeries.setOnClickListener {
            textComic.setTextColor(whiteColor)
            textEvent.setTextColor(whiteColor)
            textSeries.setTextColor(redColor)
        }
    }

    private fun onShowDialog(characterModel : CharacterModel) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(characterModel.name)
            .setMessage(characterModel.description)
            .setNegativeButton(getString(R.string.close_dialog)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.details.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    binding.progressBarDetail.setInvisible()
                    resource.data?.let { values ->
                        if (values.data.result.isNotEmpty()) {
                            comicCharacterAdapter.comics = values.data.result.toList()
                        } else {
                            toast(getString(R.string.empty_list_comics))
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

                else -> {}
            }
        }
    }

    override fun onCreateOptionsMenu(menu : Menu, inflater : MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                viewModel.insert(characterModel)
                toast(getString(R.string.saved_successfully))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onLoadCharacter(characterModel : CharacterModel) = with(binding) {
        tvNameCharacterDetails.text = characterModel.name
        if (characterModel.description.isEmpty()) {
            tvDescriptionCharacterDetails.text =
                requireContext().getString(R.string.text_description_empty)
        } else {
            tvDescriptionCharacterDetails.text = characterModel.description.limitDescription(100)
        }
        loadImage(
            imgCharacterDetails,
            characterModel.thumbnailModel.path,
            characterModel.thumbnailModel.extension
        )
    }

    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentDetailsCharacterBinding =
        FragmentDetailsCharacterBinding.inflate(inflater, container, false)

    private fun setupRecycleView() = with(binding) {
        rvComics.apply {
            adapter = comicCharacterAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }


}