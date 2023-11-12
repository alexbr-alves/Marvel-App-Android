package alex.lop.io.alexProject.fragment.detailsComic

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.adapters.CharacterComicAdapter
import alex.lop.io.alexProject.adapters.DetailsComicAdapter
import alex.lop.io.alexProject.adapters.EventCharacterAdapter
import alex.lop.io.alexProject.adapters.SeriesCharacterAdapter
import alex.lop.io.alexProject.adapters.StoriesCharacterAdapter
import alex.lop.io.alexProject.data.model.comic.ComicModel
import alex.lop.io.alexProject.databinding.FragmentDetailsComicBinding
import alex.lop.io.alexProject.fragment.BaseFragment
import alex.lop.io.alexProject.util.limitDescription
import alex.lop.io.alexProject.util.loadImage
import alex.lop.io.alexProject.util.setGone
import alex.lop.io.alexProject.util.setInvisible
import alex.lop.io.alexProject.viewModel.detailsComics.DetailsComicsViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsComicFragment :
    BaseFragment<FragmentDetailsComicBinding, DetailsComicsViewModel>() {
    override val viewModel : DetailsComicsViewModel by viewModels()
    private val args : DetailsComicFragmentArgs by navArgs()
    private lateinit var comicModel : ComicModel
    private var viewPager2 : ViewPager2? = null

    private var characterComicAdapter = CharacterComicAdapter()
    private var eventCharacterAdapter = EventCharacterAdapter()
    private var seriesCharacterAdapter = SeriesCharacterAdapter()
    private var storiesCharacterAdapter = StoriesCharacterAdapter()


    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentDetailsComicBinding =
        FragmentDetailsComicBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        comicModel = args.Comic
        onLoadComic(comicModel)
        setupViewPager()
    }

    private fun setupViewPager() {
        handleClickViewpager()
        handleViewPager()
        handleEmptyButton()
    }

    private fun handleEmptyButton() = binding.run {
        if (characterComicAdapter.itemCount == 0) {
            textCharacter.setGone()
            viewPagerComic.currentItem = 1
        }
    }

    private fun handleViewPager() {
        viewPager2 = view?.findViewById<ViewPager2>(R.id.viewPagerComic)
        val detailsComicAdapter = DetailsComicAdapter(childFragmentManager, lifecycle, comicModel.id)
        viewPager2?.adapter = detailsComicAdapter
        viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewPager2?.offscreenPageLimit = 1
                updateButtonColors(position)
            }
        })
    }

    private fun onLoadComic(comic : ComicModel) = with(binding) {
        textName.text = comic.title
        if (comic.description.isNullOrEmpty()) {
            textDescription.setGone()
        } else {
            textDescription.text =
                comic.description.limitDescription(100)
        }
        loadImage(
            imageComic,
            comic.thumbnailModel.path,
            comic.thumbnailModel.extension
        )
    }

    private fun updateButtonColors(position : Int) = binding.run {
        when (position) {
            0 -> comicActive()
            1 -> eventActive()
            2 -> seriesActive()
            3 -> storiesActive()
        }
    }

    private fun comicActive() = binding.run {
        textCharacter.setTextColor(resources.getColor(R.color.red))
        textEvent.setTextColor(resources.getColor(R.color.white))
        textSeries.setTextColor(resources.getColor(R.color.white))
        textStories.setTextColor(resources.getColor(R.color.white))
    }

    private fun eventActive() = binding.run {
        textCharacter.setTextColor(resources.getColor(R.color.white))
        textEvent.setTextColor(resources.getColor(R.color.red))
        textSeries.setTextColor(resources.getColor(R.color.white))
        textStories.setTextColor(resources.getColor(R.color.white))
    }

    private fun seriesActive() = binding.run {
        textCharacter.setTextColor(resources.getColor(R.color.white))
        textEvent.setTextColor(resources.getColor(R.color.white))
        textSeries.setTextColor(resources.getColor(R.color.red))
        textStories.setTextColor(resources.getColor(R.color.white))
    }

    private fun storiesActive() = binding.run {
        textStories.setTextColor(resources.getColor(R.color.red))
        textCharacter.setTextColor(resources.getColor(R.color.white))
        textEvent.setTextColor(resources.getColor(R.color.white))
        textSeries.setTextColor(resources.getColor(R.color.white))
    }

    private fun handleClickViewpager() = binding.run {
        textCharacter.setOnClickListener {
            viewPagerComic.currentItem = 0
        }
        textEvent.setOnClickListener {
            viewPagerComic.currentItem = 1
        }
        textSeries.setOnClickListener {
            viewPagerComic.currentItem = 2
        }
        textStories.setOnClickListener {
            viewPagerComic.currentItem = 3
        }
        updateButtonColors(viewPagerComic.currentItem)
    }

}