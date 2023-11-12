package alex.lop.io.alexProject.adapters

import alex.lop.io.alexProject.fragment.detailsCharacter.ComicsCharacterFragment
import alex.lop.io.alexProject.fragment.detailsCharacter.EventsCharacterFragment
import alex.lop.io.alexProject.fragment.detailsCharacter.SeriesCharacterFragment
import alex.lop.io.alexProject.fragment.detailsCharacter.StoriesCharacterFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsComicAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val id: Int) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ComicsCharacterFragment(id)
            1 -> EventsCharacterFragment(id)
            2 -> SeriesCharacterFragment(id)
            3 -> StoriesCharacterFragment(id)
            else -> ComicsCharacterFragment(id)
        }
    }
}