package alex.lop.io.alexProject.fragment.detailsEvent

import alex.lop.io.alexProject.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alex.lop.io.alexProject.databinding.FragmentDescriptionComicBinding
import alex.lop.io.alexProject.databinding.FragmentDescriptionEventBinding
import alex.lop.io.alexProject.databinding.FragmentDetailsEventBinding
import alex.lop.io.alexProject.util.setGone


class DescriptionEventFragment(private val description: String) : Fragment() {
    private var _binding: FragmentDescriptionEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        _binding = FragmentDescriptionEventBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() = binding.run {
        if (description.isEmpty()) {
            textDescription.text = getString(R.string.common_no_information)
        } else {
            textDescription.text = description
        }
    }

}