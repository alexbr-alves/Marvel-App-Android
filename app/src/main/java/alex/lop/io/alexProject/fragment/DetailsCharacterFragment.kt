package alex.lop.io.alexProject.fragment

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.data.model.character.CharacterModel
import alex.lop.io.alexProject.databinding.FragmentDetailsCharacterBinding
import alex.lop.io.alexProject.adapters.ComicCharacterAdapter
import alex.lop.io.alexProject.adapters.DetailsAdapter
import alex.lop.io.alexProject.adapters.EventCharacterAdapter
import alex.lop.io.alexProject.viewModel.DetailsCharacterViewModel
import alex.lop.io.alexProject.state.ResourceState
import alex.lop.io.alexProject.util.setInvisible
import alex.lop.io.alexProject.util.limitDescription
import alex.lop.io.alexProject.util.loadImage
import alex.lop.io.alexProject.util.setGone
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
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
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
    private lateinit var characterModel : CharacterModel
    private var viewPager2: ViewPager2? = null

    override fun onCreate(savedInstanceState : Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterModel = args.character
        viewPager2 = view.findViewById<ViewPager2>(R.id.viewPager2)
        val detailsAdapter = DetailsAdapter(childFragmentManager, lifecycle, characterModel.id)
        viewPager2?.adapter = detailsAdapter

        descriptionCharacter()
        onLoadCharacter(characterModel)
        handleClickViewpager()

        viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position : Int) {
                super.onPageSelected(position)
                updateButtonColors(position)
            }
        })

    }

    private fun updateButtonColors(position: Int) = binding.run {
        val redColor = resources.getColor(R.color.red)
        val whiteColor = resources.getColor(R.color.white)
        when(position) {
            0-> {
                textComic.setTextColor(redColor)
                textEvent.setTextColor(whiteColor)
                textSeries.setTextColor(whiteColor)
            }
            1 -> {
                textComic.setTextColor(whiteColor)
                textEvent.setTextColor(redColor)
                textSeries.setTextColor(whiteColor)
            }
            2 -> {
                textComic.setTextColor(whiteColor)
                textEvent.setTextColor(whiteColor)
                textSeries.setTextColor(redColor)
            }
        }

    }


    private fun handleClickViewpager() = binding.run {
        textComic.setOnClickListener {
            viewPager2.currentItem = 0
        }
        textEvent.setOnClickListener {
            viewPager2.currentItem = 1
        }
        textSeries.setOnClickListener {
            viewPager2.currentItem = 2
        }
        updateButtonColors(viewPager2.currentItem)
    }

    private fun descriptionCharacter() = binding.run {

        tvDescriptionCharacterDetails.setOnClickListener {
            onShowDialog(characterModel)
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

}