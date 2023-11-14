package alex.lop.io.alexProject.adapters

import alex.lop.io.alexProject.fragment.detailsEvent.CharactersEventFragment
import alex.lop.io.alexProject.fragment.detailsEvent.ComicEventFragment
import alex.lop.io.alexProject.fragment.detailsEvent.DescriptionEventFragment
import alex.lop.io.alexProject.fragment.detailsEvent.SeriesEventFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsEventAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val id: Int, val description: String) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DescriptionEventFragment(description)
            1 -> CharactersEventFragment(id)
            2 -> ComicEventFragment(id)
            3 -> SeriesEventFragment(id)
            else -> DescriptionEventFragment(description)
        }
    }
}