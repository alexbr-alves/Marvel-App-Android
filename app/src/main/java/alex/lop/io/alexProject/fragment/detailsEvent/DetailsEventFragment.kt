package alex.lop.io.alexProject.fragment.detailsEvent

import alex.lop.io.alexProject.R
import alex.lop.io.alexProject.adapters.DetailsEventAdapter
import alex.lop.io.alexProject.data.model.event.EventModel
import alex.lop.io.alexProject.databinding.FragmentDetailsEventBinding
import alex.lop.io.alexProject.fragment.BaseFragment
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
class DetailsEventFragment : BaseFragment<FragmentDetailsEventBinding, DetailsEventViewModel>() {
    override val viewModel : DetailsEventViewModel by viewModels()
    private val args : DetailsEventFragmentArgs by navArgs()
    private lateinit var eventModel : EventModel
    private var viewPagerEvent : ViewPager2? = null

    override fun getViewBinding(
        inflater : LayoutInflater, container : ViewGroup?
    ) : FragmentDetailsEventBinding =
        FragmentDetailsEventBinding.inflate(inflater, container, false)

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventModel = args.eventModel
        setupViewPager()
        onLoadEvent(eventModel)
    }

    private fun setupViewPager() {
        handleClickViewpager()
        handleViewPager()
    }

    private fun onLoadEvent(comic : EventModel) = with(binding) {
        textName.text = comic.title
        loadImage(
            imageEvent, comic.thumbnailModel.path, comic.thumbnailModel.extension
        )
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