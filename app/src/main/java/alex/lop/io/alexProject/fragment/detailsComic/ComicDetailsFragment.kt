package alex.lop.io.alexProject.fragment.detailsComic

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.adapters.DetailsComicAdapter
import alex.lop.io.alexProject.data.model.FavoriteModel
import alex.lop.io.alexProject.data.model.comic.ComicModel
import alex.lop.io.alexProject.databinding.FragmentDetailsComicBinding
import alex.lop.io.alexProject.fragment.BaseFragment
import alex.lop.io.alexProject.util.Converts
import alex.lop.io.alexProject.util.loadImage
import alex.lop.io.alexProject.viewModel.detailsComics.ComicDetailsViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComicDetailsFragment : BaseFragment<FragmentDetailsComicBinding, ComicDetailsViewModel>() {
    override val viewModel : ComicDetailsViewModel by viewModels()
    private val args : ComicDetailsFragmentArgs by navArgs()
    private lateinit var comicModel : ComicModel
    private var viewPager2 : ViewPager2? = null
    private lateinit var favoriteModel : FavoriteModel
    private var converts = Converts()


    override fun getViewBinding(
        inflater : LayoutInflater, container : ViewGroup?
    ) : FragmentDetailsComicBinding =
        FragmentDetailsComicBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        comicModel = args.Comic
        favoriteModel = converts.comicToFavorite(comicModel)
        viewModel.searchFavorite(favoriteModel.id)
        onLoadComic(comicModel)
        setupViewPager()
        descriptionComic()
    }

    private fun setupViewPager() {
        handleClickViewpager()
        handleViewPager()
    }

    private fun handleViewPager() {
        viewPager2 = view?.findViewById<ViewPager2>(R.id.viewPagerComic)
        val comicDescription =
            if (!comicModel.description.isNullOrEmpty()) comicModel.description else ""
        val detailsComicAdapter =
            DetailsComicAdapter(childFragmentManager, lifecycle, comicModel.id, comicDescription)
        viewPager2?.adapter = detailsComicAdapter
        viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position : Int) {
                super.onPageSelected(position)
                viewPager2?.offscreenPageLimit = 1
                updateButtonColors(position)
            }
        })
    }

    private fun onLoadComic(comic : ComicModel) = with(binding) {
        viewModel.searchCharacter.observe(viewLifecycleOwner) {
            val color = if (it) R.drawable.favorite_red else R.drawable.favorite
            binding.imageFavorite.setImageResource(color)
            textName.text = comic.title
            loadImage(
                imageComic, comic.thumbnailModel.path, comic.thumbnailModel.extension
            )
        }
    }

    private fun updateButtonColors(position : Int) = binding.run {
        val textViews = arrayOf(binding.textAbout, binding.textCharacter, binding.textEvent)
        val activeColor = ContextCompat.getColor(requireContext(), R.color.red)
        val inactiveColor = ContextCompat.getColor(requireContext(), R.color.white)

        textViews.forEachIndexed { index, textView ->
            textView.setTextColor(if (index == position) activeColor else inactiveColor)
        }
    }

    private fun handleClickViewpager() = binding.run {
        textAbout.setOnClickListener {
            viewPagerComic.currentItem = 0
        }
        textCharacter.setOnClickListener {
            viewPagerComic.currentItem = 1
        }
        textEvent.setOnClickListener {
            viewPagerComic.currentItem = 2
        }
        updateButtonColors(viewPagerComic.currentItem)
    }

    private fun descriptionComic() = binding.run {
        imageFavorite.setOnClickListener {
            viewModel.searchFavorite(favoriteModel.id)
            viewModel.searchCharacter.observe(viewLifecycleOwner) {
                if (it) {
                    viewModel.delete(favoriteModel)
                    binding.imageFavorite.setImageResource(R.drawable.favorite)
                } else {
                    viewModel.insert(favoriteModel)
                    binding.imageFavorite.setImageResource(R.drawable.favorite_red)
                }
            }
        }
    }


}