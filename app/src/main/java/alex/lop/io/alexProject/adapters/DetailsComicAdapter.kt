package alex.lop.io.alexProject.adapters

import alex.lop.io.alexProject.fragment.detailsComic.CharactersComicFragment
import alex.lop.io.alexProject.fragment.detailsComic.DescriptionComicFragment
import alex.lop.io.alexProject.fragment.detailsComic.EventComicFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsComicAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val id: Int, val description: String) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DescriptionComicFragment(description)
            1 -> CharactersComicFragment(id)
            2 -> EventComicFragment(id)
            else -> DescriptionComicFragment(description)
        }
    }
}