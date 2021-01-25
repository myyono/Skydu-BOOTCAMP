package skydu.android.instaclone.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_profile_detail.view.*
import skydu.android.instaclone.R
import skydu.android.instaclone.data.repository.model.Profile

class ProfileDetailItemViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_profile_detail, parent, false)) {

    fun bind(data: Profile) {

        Glide
            .with(itemView.ivProfile.context)
            .load(data.user_profile_image_url)
            .apply(RequestOptions.circleCropTransform())
            .apply(RequestOptions.placeholderOf(R.drawable.ic_profile_selected))
            .apply(RequestOptions.errorOf(R.drawable.ic_profile_selected))
            .into(itemView.ivProfile)

        itemView.tvUserName.text = data.username
        itemView.tvFullName.text = data.name
    }
}