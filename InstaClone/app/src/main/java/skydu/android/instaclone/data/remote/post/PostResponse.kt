package skydu.android.instaclone.data.remote.post

data class PostsResponse(
    val id: Int,
    val image_url: String,
    val caption: String,
    val comments_count: Int,
    val likes_info: LikesInfo,
    val username: String,
    val user_image_url: String?,
    val created_at: String
) {
    class LikesInfo(
        val count: Int,
        val is_liked: Boolean
    )
}