package alex.lop.io.alexProject.fragment.detailsComic

import alex.lop.io.alexProject.databinding.FragmentDetailsComicBinding
import alex.lop.io.alexProject.fragment.BaseFragment
import alex.lop.io.alexProject.viewModel.detailsComics.DetailsComicsViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsComicFragment :
    BaseFragment<FragmentDetailsComicBinding, DetailsComicsViewModel>() {
    override val viewModel : DetailsComicsViewModel by viewModels()


    override fun getViewBinding(
        inflater : LayoutInflater,
        container : ViewGroup?
    ) : FragmentDetailsComicBinding = FragmentDetailsComicBinding.inflate(inflater, container, false)

}