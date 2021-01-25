package skydu.android.instaclone.data.remote.post

data class PostResponseWithComment(

    val id: Int,
    val image_url: String,
    val caption: String,
    val comments_count: Int,
    val likes_info: PostsResponse.LikesInfo,
    val username: String,
    val user_image_url: String?,
    val created_at: String,
    val comments: List<Comment>
) {
    class LikesInfo(
        val count: Int,
        val is_liked: Boolean
    )

    class Comment(
        val id: Int,
        val user_id: Int,
        val post_id: Int,
        val text: String,
        val created_at: String
    )
}