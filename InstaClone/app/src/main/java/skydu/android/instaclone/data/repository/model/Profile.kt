package skydu.android.instaclone.data.repository.model
class Profile (
   val username: String,
   val name: String,
   val email: String,
   val user_profile_image_url: String,
   val posts: List<Post>
) {
   class Post(val id: Int,  val image_url: String)
}
