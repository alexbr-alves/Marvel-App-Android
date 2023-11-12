package alex.lop.io.alexProject.adapters

import alex.lop.io.alexProject.data.model.comic.ComicModel
import alex.lop.io.alexProject.databinding.LayoutCardNameBinding
import alex.lop.io.alexProject.databinding.LayoutCardNameDescriptionBinding
import alex.lop.io.alexProject.util.loadImage
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ComicsAdapter : RecyclerView.Adapter<ComicsAdapter.ComicsViewHolder>() {

    inner class ComicsViewHolder(val binding : LayoutCardNameBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ComicModel>() {
        override fun areItemsTheSame(oldItem : ComicModel, newItem : ComicModel) : Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem : ComicModel,
            newItem : ComicModel
        ) : Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var comicList: List<ComicModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemCount() : Int {
        return comicList.size
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ComicsViewHolder {
        return ComicsViewHolder(
            LayoutCardNameBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private var onItemClickListener : ((ComicModel) -> Unit)? = null

    fun setOnClickListener(listener : (ComicModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder : ComicsViewHolder, position : Int) {
        val comic = comicList[position]
        holder.binding.apply {
            textName.text = comic.title
            loadImage(
                image,
                comic.thumbnailModel.path,
                comic.thumbnailModel.extension
            )
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(comic)
            }
        }
    }
}