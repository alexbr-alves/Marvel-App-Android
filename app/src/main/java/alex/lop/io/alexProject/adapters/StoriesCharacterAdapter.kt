package alex.lop.io.alexProject.adapters

import alex.lop.io.alexProject.data.model.stories.StoriesModel
import alex.lop.io.alexProject.databinding.LayoutMiniCardsBinding
import alex.lop.io.alexProject.util.setGone
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class StoriesCharacterAdapter : RecyclerView.Adapter<StoriesCharacterAdapter.StoriesViewHolder>() {

    inner class StoriesViewHolder(val binding: LayoutMiniCardsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<StoriesModel>() {

        override fun areItemsTheSame(oldItem: StoriesModel, newItem: StoriesModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem: StoriesModel,
            newItem: StoriesModel
        ): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var stories: List<StoriesModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemCount(): Int {
        return stories.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        return StoriesViewHolder(
            LayoutMiniCardsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        val storie = stories[position]
        holder.binding.apply {
            textName.text = storie.title
            textDescription.setGone()
            image.setGone()
        }
    }
}
