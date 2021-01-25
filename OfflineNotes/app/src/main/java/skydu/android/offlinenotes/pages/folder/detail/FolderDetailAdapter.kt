package skydu.android.offlinenotes.pages.folder.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import skydu.android.offlinenotes.data.Folder
import skydu.android.offlinenotes.data.Notes
import skydu.android.offlinenotes.databinding.FolderItemBinding
import skydu.android.offlinenotes.databinding.NotesItemBinding
import skydu.android.offlinenotes.pages.folder.FolderListViewHolder

class FolderDetailAdapter (
    private val onItemClick: (Notes) -> Unit,
) : RecyclerView.Adapter<FolderDetailViewHolder>() {

    private val notes: MutableList<Notes> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FolderDetailViewHolder(NotesItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: FolderDetailViewHolder, position: Int) {
        val note = notes[position]
        holder.setData(
            note
        )
        holder.binding.root.setOnClickListener {
            onItemClick(note)
        }
    }

    override fun getItemCount(): Int = notes.size

    fun submitData(data: List<Notes>) {
        notes.clear()
        notes.addAll(data)
        notifyDataSetChanged()
    }
    }