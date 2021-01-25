package skydu.android.instaclone.data.remote.profile

import skydu.android.instaclone.data.remote.post.PostsResponse

data class ProfileResponse(
    val username: String,
    val user_image_url: String?,
    val name: String,
    val email: String,
    val posts: List<PostsResponse>
)