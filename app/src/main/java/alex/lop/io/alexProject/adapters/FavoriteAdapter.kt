package alex.lop.io.alexProject.adapters


import alex.lop.io.alexProject.data.model.FavoriteModel
import alex.lop.io.alexProject.databinding.LayoutCardNameDescriptionBinding
import alex.lop.io.alexProject.util.Constants
import alex.lop.io.alexProject.util.limitDescription


import alex.lop.io.alexProject.util.loadImage
import alex.lop.io.alexProject.util.setGone
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(val binding : LayoutCardNameDescriptionBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<FavoriteModel>() {
        override fun areItemsTheSame(oldItem : FavoriteModel, newItem : FavoriteModel) : Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem : FavoriteModel,
            newItem : FavoriteModel
        ) : Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.type == newItem.type &&
                    oldItem.description == newItem.description &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var favorites : List<FavoriteModel>
        get() = differ.currentList
        set(value) = differ.submitList(value.filter { it.thumbnailModel.path != Constants.IMAGE_NOT_AVAILABLE })

    override fun getItemCount() : Int = favorites.size
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : CharacterViewHolder {
        return CharacterViewHolder(
            LayoutCardNameDescriptionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder : CharacterViewHolder, position : Int) {
        val favorite = favorites[position]
        holder.binding.apply {
            name.text = favorite.title.limitDescription(25)
            if (favorite.description.isNullOrEmpty()) {
                description.setGone()
            } else {
                description.text = favorite.description.limitDescription(80)
            }
            type.text = favorite.type
            loadImage(
                image,
                favorite.thumbnailModel.path,
                favorite.thumbnailModel.extension
            )
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(favorite)
            }
        }
    }

    private var onItemClickListener : ((FavoriteModel) -> Unit)? = null

    fun setOnClickListener(listener : (FavoriteModel) -> Unit) {
        onItemClickListener = listener
    }

    fun getCharacterPosition(position : Int) : FavoriteModel {
        return favorites[position]
    }
}