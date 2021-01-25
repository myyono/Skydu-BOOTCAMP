package skydu.android.offlinenotes.pages.folder.detail

import androidx.recyclerview.widget.RecyclerView
import skydu.android.offlinenotes.data.Folder
import skydu.android.offlinenotes.data.Notes
import skydu.android.offlinenotes.databinding.FolderItemBinding
import skydu.android.offlinenotes.databinding.NotesItemBinding
import skydu.android.offlinenotes.util.DateUtil

class FolderDetailViewHolder (val binding: NotesItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun setData(
        note: Notes,
    ) {
        binding.judul.text = note.judul
        binding.isi.text = note.isi
    }

}