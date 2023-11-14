package alex.lop.io.alexProject.adapters


import alex.lop.io.alexProject.fragment.detailsCharacter.ComicsCharacterFragment
import alex.lop.io.alexProject.fragment.detailsCharacter.DescriptionCharacterFragment
import alex.lop.io.alexProject.fragment.detailsCharacter.EventsCharacterFragment
import alex.lop.io.alexProject.fragment.detailsCharacter.SeriesCharacterFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsCharacterAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val id: Int, val description: String) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DescriptionCharacterFragment(description)
            1 -> ComicsCharacterFragment(id)
            2 -> EventsCharacterFragment(id)
            3 -> SeriesCharacterFragment(id)
            else -> ComicsCharacterFragment(id)
        }
    }
}