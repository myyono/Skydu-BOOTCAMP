package skydu.android.instaclone.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_profile_post.view.*
import skydu.android.instaclone.R
import skydu.android.instaclone.data.repository.model.Profile

class ProfilePostItemViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_profile_post, parent, false)) {


    fun bind(data: Profile.Post, onClickListener: ((Profile.Post) -> Unit)) {
        itemView.setOnClickListener {
            onClickListener(data)
        }
        Glide
            .with(itemView.iv_post.context)
            .load(data.image_url)
            .apply(RequestOptions.circleCropTransform())
            .apply(RequestOptions.placeholderOf(R.drawable.ic_photo))
            .apply(RequestOptions.errorOf(R.drawable.ic_photo))
            .into(itemView.iv_post)
    }
}