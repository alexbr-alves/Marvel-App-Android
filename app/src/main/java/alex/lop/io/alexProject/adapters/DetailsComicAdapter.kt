package alex.lop.io.alexProject.adapters

import alex.lop.io.alexProject.fragment.detailsComic.CharactersComicFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsComicAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val id: Int) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CharactersComicFragment(id)
            else -> CharactersComicFragment(id)
        }
    }
}