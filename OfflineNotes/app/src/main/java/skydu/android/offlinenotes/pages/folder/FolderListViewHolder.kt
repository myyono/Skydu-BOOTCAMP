package skydu.android.offlinenotes.pages.folder

import androidx.recyclerview.widget.RecyclerView
import skydu.android.offlinenotes.data.Folder
import skydu.android.offlinenotes.databinding.FolderItemBinding

class FolderListViewHolder (val binding: FolderItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun setData(
        folder: Folder,
    ) {
        binding.txtName.text = folder.judul
    }

}