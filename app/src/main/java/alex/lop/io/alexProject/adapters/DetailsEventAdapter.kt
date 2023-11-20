package alex.lop.io.alexProject.adapters

import alex.lop.io.alexProject.fragment.detailsEvent.EventCharactersFragment
import alex.lop.io.alexProject.fragment.detailsEvent.EventComicsFragment
import alex.lop.io.alexProject.fragment.detailsEvent.EventDescriptionFragment
import alex.lop.io.alexProject.fragment.detailsEvent.EventSeriesFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsEventAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val id: Int, val description: String) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EventDescriptionFragment(description)
            1 -> EventCharactersFragment(id)
            2 -> EventComicsFragment(id)
            3 -> EventSeriesFragment(id)
            else -> EventDescriptionFragment(description)
        }
    }
}