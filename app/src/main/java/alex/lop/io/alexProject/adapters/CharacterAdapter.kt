package alex.lop.io.alexProject.adapters


import alex.lop.io.alexProject.data.model.character.CharacterModel
import alex.lop.io.alexProject.databinding.LayoutCardNameBinding
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

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(val binding : LayoutCardNameDescriptionBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<CharacterModel>() {
        override fun areItemsTheSame(oldItem : CharacterModel, newItem : CharacterModel) : Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem : CharacterModel,
            newItem : CharacterModel
        ) : Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.name == newItem.name &&
                    oldItem.description == newItem.description &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var characters : List<CharacterModel>
        get() = differ.currentList
        set(value) = differ.submitList(value.filter { it.thumbnailModel.path != Constants.IMAGE_NOT_AVAILABLE })

    override fun getItemCount() : Int = characters.size
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : CharacterViewHolder {
        return CharacterViewHolder(
            LayoutCardNameDescriptionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder : CharacterViewHolder, position : Int) {
        val character = characters[position]
        holder.binding.apply {
            name.text = character.name.limitDescription(15)
            if (character.description.isNullOrEmpty()) {
                description.setGone()
            } else {
                description.text = character.description.limitDescription(40)
            }
            loadImage(
                image,
                character.thumbnailModel.path,
                character.thumbnailModel.extension
            )
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(character)
            }
        }
    }

    private var onItemClickListener : ((CharacterModel) -> Unit)? = null

    fun setOnClickListener(listener : (CharacterModel) -> Unit) {
        onItemClickListener = listener
    }

    fun getCharacterPosition(position : Int) : CharacterModel {
        return characters[position]
    }
}