package skydu.android.offlinenotes.pages.folder.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import skydu.android.offlinenotes.data.Folder
import skydu.android.offlinenotes.databinding.FolderItemBinding
import skydu.android.offlinenotes.pages.folder.FolderListViewHolder

class FolderListAdapter (
    private val onItemClick: (Folder) -> Unit,
) : RecyclerView.Adapter<FolderListViewHolder>() {

    private val folders: MutableList<Folder> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FolderListViewHolder(FolderItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: FolderListViewHolder, position: Int) {
        val folder = folders[position]
        holder.setData(
           folder
        )
        holder.binding.root.setOnClickListener {
            onItemClick(folder)
        }
    }

    override fun getItemCount(): Int = folders.size

    fun submitData(data: List<Folder>) {
        folders.clear()
        folders.addAll(data)
        notifyDataSetChanged()
    }
    }