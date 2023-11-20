package alex.lop.io.alexProject.fragment.detailsEvent

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.adapters.DetailsEventAdapter
import alex.lop.io.alexProject.data.model.FavoriteModel
import alex.lop.io.alexProject.data.model.event.EventModel
import alex.lop.io.alexProject.databinding.FragmentDetailsEventBinding
import alex.lop.io.alexProject.fragment.BaseFragment
import alex.lop.io.alexProject.util.Converts
import alex.lop.io.alexProject.util.loadImage
import alex.lop.io.alexProject.viewModel.detailEvent.DetailsEventViewModel
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
class EventDetailsFragment : BaseFragment<FragmentDetailsEventBinding, DetailsEventViewModel>() {
    override val viewModel : DetailsEventViewModel by viewModels()
    private val args : EventDetailsFragmentArgs by navArgs()
    private lateinit var eventModel : EventModel
    private var viewPagerEvent : ViewPager2? = null
    private lateinit var favoriteModel : FavoriteModel
    private val converts = Converts()

    override fun getViewBinding(
        inflater : LayoutInflater, container : ViewGroup?
    ) : FragmentDetailsEventBinding =
        FragmentDetailsEventBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventModel = args.eventModel
        favoriteModel = converts.eventToFavorite(eventModel)
        viewModel.searchFavorite(favoriteModel.id)
        setupViewPager()
        onLoadEvent(eventModel)
        descriptionEvent()
    }

    private fun setupViewPager() {
        handleClickViewpager()
        handleViewPager()
    }

    private fun onLoadEvent(comic : EventModel) = with(binding) {
        viewModel.searchCharacter.observe(viewLifecycleOwner) {
            val color = if (it) R.drawable.favorite_red else R.drawable.favorite
            binding.imageFavorite.setImageResource(color)
            textName.text = comic.title
            loadImage(
                imageEvent, comic.thumbnailModel.path, comic.thumbnailModel.extension
            )
        }
    }

    private fun descriptionEvent() = binding.run {
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

    private fun handleViewPager() {
        viewPagerEvent = view?.findViewById<ViewPager2>(R.id.viewPagerEvent)
        val comicDescription =
            if (!eventModel.description.isNullOrEmpty()) eventModel.description else ""
        val detailsComicAdapter =
            DetailsEventAdapter(childFragmentManager, lifecycle, eventModel.id, comicDescription)
        viewPagerEvent?.adapter = detailsComicAdapter
        viewPagerEvent?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position : Int) {
                super.onPageSelected(position)
                viewPagerEvent?.offscreenPageLimit = 1
                updateButtonColors(position)
            }
        })
    }

    private fun updateButtonColors(position : Int) = binding.run {
        val textViews =
            arrayOf(binding.textAbout, binding.textCharacter, binding.textComic, binding.textSeries)
        val activeColor = ContextCompat.getColor(requireContext(), R.color.red)
        val inactiveColor = ContextCompat.getColor(requireContext(), R.color.white)

        textViews.forEachIndexed { index, textView ->
            textView.setTextColor(if (index == position) activeColor else inactiveColor)
        }
    }

    private fun handleClickViewpager() = binding.run {
        textAbout.setOnClickListener {
            viewPagerEvent.currentItem = 0
        }
        textCharacter.setOnClickListener {
            viewPagerEvent.currentItem = 1
        }
        textComic.setOnClickListener {
            viewPagerEvent.currentItem = 2
        }
        textSeries.setOnClickListener {
            viewPagerEvent.currentItem = 3
        }
        updateButtonColors(viewPagerEvent.currentItem)
    }

}