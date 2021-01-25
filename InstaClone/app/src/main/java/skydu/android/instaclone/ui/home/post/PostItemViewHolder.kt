package skydu.android.instaclone.ui.home.post

import android.content.res.Resources
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_view_post.view.*
import skydu.android.instaclone.R
import skydu.android.instaclone.data.repository.model.PostViewData
import skydu.android.instaclone.utils.TextViewClickUtil

class PostItemViewHolder(
    parent: ViewGroup,
    val onProfileClicked: ((PostViewData) -> Unit),
    val onLikeClickListener: ((PostViewData) -> Unit),
    val onShareClickListener: ((PostViewData) -> Unit),
    val onPostClickListener: ((PostViewData) -> Unit)
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_view_post, parent, false)
) {

    fun bind(data: PostViewData, position: Int) {
        itemView.tvName.text = data.username

        val caption = data.caption

        val text = SpannableStringBuilder()
            .bold {
                append(data.username)
            }
            .append(" ")
            .append(caption)

        TextViewClickUtil.setClickableText(
            itemView.tvCaption,
            text,
            listOf(
                TextViewClickUtil.Clickable(
                    0,
                    data.username.length) {
                    onProfileClicked(data)
                },
                TextViewClickUtil.Clickable(
                    data.username.length + 1,
                    text.length) {
                    onPostClickListener(data)
                }
            )
        )

        itemView.tvTime.text = data.created_at
        itemView.tvLikesCount.text =
            itemView.context.getString(R.string.post_like_label, data.likes_count)

        Glide
            .with(itemView.ivProfile.context)
            .let {
                if (data.user_profile_image_url != null) {
                    it.load(data.user_profile_image_url)
                } else {
                    it.load(R.drawable.ic_profile_selected)
                }
            }
            .apply(RequestOptions.circleCropTransform())
            .apply(RequestOptions.placeholderOf(R.drawable.ic_profile_selected))
            .apply(RequestOptions.errorOf(R.drawable.ic_profile_selected))
            .into(itemView.ivProfile)

        data.image_url.run {
            val glideRequest = Glide
                .with(itemView.ivPost.context)
                .load(this)

            val screenWidth = Resources.getSystem().displayMetrics.widthPixels

            glideRequest
                .apply(RequestOptions.overrideOf(screenWidth, screenWidth))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_photo))
                .apply(RequestOptions.errorOf(R.drawable.ic_photo))
                .into(itemView.ivPost)
        }

        itemView.context.getString(R.string.post_like_label, data.likes_count)
        if (data.is_liked) {
            itemView.ivLike.setImageResource(R.drawable.ic_heart_selected)
        } else {
            itemView.ivLike.setImageResource(R.drawable.ic_heart_unselected)
        }

        itemView.tvName.setOnClickListener {
            onProfileClicked(data)
        }

        itemView.ivLike.setOnClickListener {
            onLikeClickListener(data)
        }

        itemView.ivShare.setOnClickListener {
            onShareClickListener(data)
        }

        itemView.tvAddComment.setOnClickListener {
            onPostClickListener(data)
        }
    }
}