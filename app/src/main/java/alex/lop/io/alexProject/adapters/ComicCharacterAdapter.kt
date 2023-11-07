package alex.lop.io.alexProject.adapters

import alex.lop.io.alexProject.data.model.comicsCharacter.ComicsCharacterModel
import alex.lop.io.alexProject.databinding.ItemComicsCharacterBinding
import alex.lop.io.alexProject.util.loadImage
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ComicCharacterAdapter : RecyclerView.Adapter<ComicCharacterAdapter.ComicViewHolder>() {

    inner class ComicViewHolder(val binding : ItemComicsCharacterBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ComicsCharacterModel>() {

        override fun areItemsTheSame(oldItem : ComicsCharacterModel, newItem : ComicsCharacterModel) : Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem : ComicsCharacterModel,
            newItem : ComicsCharacterModel
        ) : Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var comics : List<ComicsCharacterModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemCount(): Int {
        return comics.size
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ComicViewHolder {
        return ComicViewHolder(
            ItemComicsCharacterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder : ComicViewHolder, position : Int) {
        val comic = comics[position]
        holder.binding.apply {
            tvNameComic.text = comic.title
            tvDescriptionComic.text = comic.description

            loadImage(
                imgComic,
                comic.thumbnailModel.path,
                comic.thumbnailModel.extension
            )
        }

    }

}