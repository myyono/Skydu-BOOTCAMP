package skydu.android.instaclone.data.repository.model

data class PostViewData(
    val id: Int,
    val username: String,
    val user_profile_image_url: String?,
    val image_url: String,
    val caption: String,
    var likes_count: Int,
    var is_liked: Boolean,
    val created_at: String
)