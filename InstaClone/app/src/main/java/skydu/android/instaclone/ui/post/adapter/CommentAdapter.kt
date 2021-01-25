package skydu.android.instaclone.ui.post.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import skydu.android.instaclone.data.repository.model.CommentViewData

class CommentAdapter(

    private val onProfileClicked: ((CommentViewData) -> Unit)
) : RecyclerView.Adapter<CommentItemViewHolder>() {

    private val dataList: ArrayList<CommentViewData> = ArrayList()

    override fun getItemCount(): Int = dataList.size

    fun updateList(list: List<CommentViewData>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CommentItemViewHolder(parent, onProfileClicked)

    override fun onBindViewHolder(holder: CommentItemViewHolder, position: Int) {
        holder.bind(dataList[position], position)
    }
}