package alex.lop.io.alexProject.adapters

import alex.lop.io.alexProject.data.model.creator.CreatorModel
import alex.lop.io.alexProject.databinding.ItemCreatorBinding
import alex.lop.io.alexProject.util.loadImage
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class CreatorAdapter : RecyclerView.Adapter<CreatorAdapter.CreatorViewHolder>() {

    inner class CreatorViewHolder(val binding : ItemCreatorBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<CreatorModel>() {
        override fun areItemsTheSame(oldItem : CreatorModel, newItem : CreatorModel) : Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem : CreatorModel, newItem : CreatorModel) : Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.firstName == newItem.firstName &&
                    oldItem.lastName == newItem.lastName &&
                    oldItem.fullName == newItem.fullName &&
                    oldItem.suffix == newItem.suffix &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension

        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var creatorList : List<CreatorModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : CreatorViewHolder {
        return CreatorViewHolder(
            ItemCreatorBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() : Int {
        return creatorList.size
    }

    override fun onBindViewHolder(holder : CreatorViewHolder, position : Int) {
        val creator = creatorList[position]
        holder.binding.apply {
            tvNameComic.text = "${creator.firstName} ${creator.lastName}"
            tvDescriptionComic.text = creator.suffix

            loadImage(
                imgComic,
                creator.thumbnailModel.path,
                creator.thumbnailModel.extension
            )
        }

    }
}