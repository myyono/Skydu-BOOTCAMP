package skydu.android.instaclone.data.repository.model

data class CommentViewData(
    val id: Int,

    val username: String,

    val user_image_url: String?,

    val caption: String,

    val createdAt: String
)