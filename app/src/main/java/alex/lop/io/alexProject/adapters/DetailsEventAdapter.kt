package alex.lop.io.alexProject.adapters

import alex.lop.io.alexProject.fragment.detailsComic.CharactersComicFragment
import alex.lop.io.alexProject.fragment.detailsComic.CreatorComicFragment
import alex.lop.io.alexProject.fragment.detailsComic.DescriptionComicFragment
import alex.lop.io.alexProject.fragment.detailsComic.EventComicFragment
import alex.lop.io.alexProject.fragment.detailsComic.StoriesComicFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsEventAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val id: Int, val description: String) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DescriptionComicFragment(description)
            1 -> CharactersComicFragment(id)
            2 -> CreatorComicFragment(id)
            3 -> EventComicFragment(id)
            4 -> StoriesComicFragment(id)
            else -> DescriptionComicFragment(description)
        }
    }
}