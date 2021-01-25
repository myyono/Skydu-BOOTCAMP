package skydu.android.instaclone.ui.profile.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import skydu.android.instaclone.data.repository.model.Profile

class ProfileAdapter(val onPostClickListener: ((Profile.Post) -> Unit)) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: Profile? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    companion object {
        const val VIEW_TYPE_HEADER = 1
        const val VIEW_TYPE_ITEM = 2
    }

    override fun getItemCount(): Int {
        return if (data == null) {
            0
        } else {
            data?.posts?.size ?: 0 + 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_HEADER) {
            return ProfileDetailItemViewHolder(parent)
        } else {
            return ProfilePostItemViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProfileDetailItemViewHolder) {
            holder.bind(data!!)
        } else if (holder is ProfilePostItemViewHolder) {
            val post = data!!.posts[position - 1]
            holder.bind(post, onPostClickListener)
        }
    }
}