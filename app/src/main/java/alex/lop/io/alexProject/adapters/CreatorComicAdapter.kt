package alex.lop.io.alexProject.adapters

import alex.lop.io.alexProject.data.model.creator.CreatorModel
import alex.lop.io.alexProject.databinding.LayoutMiniCardsBinding
import alex.lop.io.alexProject.util.limitDescription
import alex.lop.io.alexProject.util.loadImage
import alex.lop.io.alexProject.util.setGone
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class CreatorComicAdapter : RecyclerView.Adapter<CreatorComicAdapter.CreatorViewHolder>() {

    inner class CreatorViewHolder(val binding: LayoutMiniCardsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<CreatorModel>() {

        override fun areItemsTheSame(oldItem: CreatorModel, newItem: CreatorModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem: CreatorModel,
            newItem: CreatorModel
        ): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.firstName == newItem.firstName &&
                    oldItem.fullName == newItem.fullName &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var creators: List<CreatorModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemCount(): Int {
        return creators.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatorViewHolder {
        return CreatorViewHolder(
            LayoutMiniCardsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CreatorViewHolder, position: Int) {
        val creator = creators[position]
        holder.binding.apply {
            textName.text = creator.firstName
            if (creator.fullName.isNullOrEmpty()) {
                textDescription.setGone()
            } else {
                textDescription.text = creator.fullName.limitDescription(50)
            }

            loadImage(
                image,
                creator.thumbnailModel.path,
                creator.thumbnailModel.extension
            )
        }
    }
}
