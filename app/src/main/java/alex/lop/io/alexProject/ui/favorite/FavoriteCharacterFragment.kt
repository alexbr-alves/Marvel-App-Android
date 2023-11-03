package alex.lop.io.alexProject.ui.favorite

import alex.lop.io.alexProject.databinding.FragmentFavoriteCharacterBinding
import alex.lop.io.alexProject.ui.base.BaseFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels

class FavoriteCharacterFragment :  BaseFragment<FragmentFavoriteCharacterBinding, FavoriteCharacterViewModel>() {
    override val viewModel: FavoriteCharacterViewModel by viewModels()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteCharacterBinding = FragmentFavoriteCharacterBinding.inflate(inflater, container, false)
}