package alex.lop.io.alexProject.fragment.detailsCharacter

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.data.model.character.CharacterModel
import alex.lop.io.alexProject.databinding.FragmentDetailsCharacterBinding
import alex.lop.io.alexProject.adapters.DetailsCharacterAdapter
import alex.lop.io.alexProject.fragment.BaseFragment
import alex.lop.io.alexProject.viewModel.detailCharacter.DetailsCharacterViewModel
import alex.lop.io.alexProject.util.loadImage
import alex.lop.io.alexProject.util.toast
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsCharacterFragment :
    BaseFragment<FragmentDetailsCharacterBinding, DetailsCharacterViewModel>() {
    override val viewModel : DetailsCharacterViewModel by viewModels()
    private val args : DetailsCharacterFragmentArgs by navArgs()
    private lateinit var characterModel : CharacterModel
    private var viewPager2 : ViewPager2? = null

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterModel = args.character
        descriptionCharacter()
        onLoadCharacter(characterModel)
        viewModel.searchFavorite(characterModel.id)
        setupViewPager()
    }

    private fun setupViewPager() {
        handleClickViewpager()
        handleViewPager()
    }

    private fun handleViewPager() {
        viewPager2 = view?.findViewById<ViewPager2>(R.id.viewPager2)
        val characterDescription = if (!characterModel.description.isNullOrEmpty()) characterModel.description else ""
        val detailsCharacterAdapter = DetailsCharacterAdapter(childFragmentManager, lifecycle, characterModel.id, characterDescription)
        viewPager2?.adapter = detailsCharacterAdapter
        viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position : Int) {
                super.onPageSelected(position)
                viewPager2?.offscreenPageLimit = 1
                updateButtonColors(position)
            }
        })
    }

    private fun updateButtonColors(position : Int) = binding.run {
        when (position) {
            0 -> aboutActive()
            1 -> characterActive()
            2 -> eventActive()
            3 -> seriesActive()
        }
    }

    private fun aboutActive() = binding.run {
        textAbout.setTextColor(resources.getColor(R.color.black))
        textComic.setTextColor(resources.getColor(R.color.white))
        textEvent.setTextColor(resources.getColor(R.color.white))
        textSeries.setTextColor(resources.getColor(R.color.white))
    }

    private fun characterActive() = binding.run {
        textAbout.setTextColor(resources.getColor(R.color.white))
        textComic.setTextColor(resources.getColor(R.color.black))
        textEvent.setTextColor(resources.getColor(R.color.white))
        textSeries.setTextColor(resources.getColor(R.color.white))
    }

    private fun eventActive() = binding.run {
        textAbout.setTextColor(resources.getColor(R.color.white))
        textComic.setTextColor(resources.getColor(R.color.white))
        textEvent.setTextColor(resources.getColor(R.color.black))
        textSeries.setTextColor(resources.getColor(R.color.white))
    }

    private fun seriesActive() = binding.run {
        textAbout.setTextColor(resources.getColor(R.color.white))
        textComic.setTextColor(resources.getColor(R.color.white))
        textEvent.setTextColor(resources.getColor(R.color.white))
        textSeries.setTextColor(resources.getColor(R.color.black))
    }

    private fun handleClickViewpager() = binding.run {
        textAbout.setOnClickListener {
            viewPager2.currentItem = 0
        }
        textComic.setOnClickListener {
            viewPager2.currentItem = 1
        }
        textEvent.setOnClickListener {
            viewPager2.currentItem = 2
        }
        textSeries.setOnClickListener {
            viewPager2.currentItem = 3
        }
        updateButtonColors(viewPager2.currentItem)
    }

    private fun descriptionCharacter() = binding.run {
        imageFavorite.setOnClickListener {
            viewModel.searchCharacter.observe(viewLifecycleOwner) {
                if (it) {
                    viewModel.delete(characterModel)
                    binding.imageFavorite.setImageResource(R.drawable.favorite)
                } else {
                    viewModel.insert(characterModel)
                    binding.imageFavorite.setImageResource(R.drawable.favorite_red)
                }
            }
        }
    }

    private fun onShowDialog(characterModel : CharacterModel) {
        MaterialAlertDialogBuilder(requireContext()).setTitle(characterModel.name)
            .setMessage(characterModel.description)
            .setNegativeButton(getString(R.string.close_dialog)) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun onLoadCharacter(characterModel : CharacterModel) = with(binding) {
        viewModel.searchCharacter.observe(viewLifecycleOwner) {
            val color = if (it) R.drawable.favorite_red else R.drawable.favorite
            binding.imageFavorite.setImageResource(color)
            tvNameCharacterDetails.text = characterModel.name
            loadImage(
                imgCharacterDetails,
                characterModel.thumbnailModel.path,
                characterModel.thumbnailModel.extension
            )
        }
    }


    override fun getViewBinding(
        inflater : LayoutInflater, container : ViewGroup?
    ) : FragmentDetailsCharacterBinding =
        FragmentDetailsCharacterBinding.inflate(inflater, container, false)

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

}