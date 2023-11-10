package alex.lop.io.alexProject.adapters


import alex.lop.io.alexProject.data.model.stories.StoriesModel
import alex.lop.io.alexProject.databinding.LayoutCardNameBinding
import alex.lop.io.alexProject.databinding.LayoutNameDescritionBinding
import alex.lop.io.alexProject.util.limitDescription
import alex.lop.io.alexProject.util.setGone
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class StoriesAdapter :  RecyclerView.Adapter<StoriesAdapter.StoriesViewHolder>() {

    inner class StoriesViewHolder(val binding : LayoutNameDescritionBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<StoriesModel>() {
        override fun areItemsTheSame(oldItem : StoriesModel, newItem : StoriesModel) : Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem : StoriesModel,
            newItem : StoriesModel
        ) : Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.description == newItem.description &&
                    oldItem.title == newItem.title &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path &&
                    oldItem.thumbnailModel.extension == newItem.thumbnailModel.extension
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var storiesList: List<StoriesModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : StoriesViewHolder {
       return StoriesViewHolder(
           LayoutNameDescritionBinding.inflate(
               LayoutInflater.from(parent.context), parent, false
           )
       )
    }

    override fun getItemCount() : Int {
        return storiesList.size
    }

    override fun onBindViewHolder(holder : StoriesViewHolder, position : Int) {
        val storie = storiesList[position]
        holder.binding.apply {
            textName.text = storie.title.limitDescription(50)
           if (!storie.title.isNullOrEmpty()) {
               textDescription.text = storie.title.limitDescription(100)
           }

        }
    }
}