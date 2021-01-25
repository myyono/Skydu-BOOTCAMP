package skydu.android.instaclone.ui.post.adapter

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_view_comment.view.*
import skydu.android.instaclone.R
import skydu.android.instaclone.data.repository.model.CommentViewData
import skydu.android.instaclone.utils.TextViewClickUtil

class CommentItemViewHolder(
    parent: ViewGroup,
    private val onProfileClicked: ((CommentViewData) -> Unit),
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_view_comment, parent, false)
) {


    fun bind(data: CommentViewData, position: Int) {
        val text = SpannableStringBuilder().bold {
            append(data.username)
        }
            .append(" ")
            .append(data.caption)

        itemView.tvCommentDetail.text = text

        TextViewClickUtil.setClickableText(
            itemView.tvCommentDetail,
            text,
            listOf(
                TextViewClickUtil.Clickable(
                    0,
                    data.username.length) {
                    onProfileClicked(data)
                }
            )
        )

        itemView.tvCommentTime.text = data.createdAt

        Glide
            .with(itemView.ivProfile.context)
            .let {
                if (data.user_image_url != null) {
                    it.load(data.user_image_url)
                } else {
                    it.load(R.drawable.ic_profile_selected)
                }
            }
            .apply(RequestOptions.circleCropTransform())
            .apply(RequestOptions.placeholderOf(R.drawable.ic_profile_selected))
            .apply(RequestOptions.errorOf(R.drawable.ic_profile_selected))
            .into(itemView.ivProfile)
    }
}