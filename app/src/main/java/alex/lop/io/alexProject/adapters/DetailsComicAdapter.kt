package alex.lop.io.alexProject.adapters

import alex.lop.io.alexProject.fragment.detailsComic.ComicCharactersFragment
import alex.lop.io.alexProject.fragment.detailsComic.ComicDescriptionFragment
import alex.lop.io.alexProject.fragment.detailsComic.ComicEventFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsComicAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val id: Int, val description: String) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ComicDescriptionFragment(description)
            1 -> ComicCharactersFragment(id)
            2 -> ComicEventFragment(id)
            else -> ComicDescriptionFragment(description)
        }
    }
}