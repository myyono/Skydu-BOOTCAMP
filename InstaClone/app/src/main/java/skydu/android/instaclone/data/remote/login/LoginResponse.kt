package skydu.android.instaclone.data.remote.login

data class LoginResponse(
    var token: String,

    var profile: User

){
    class User (
        val username: String,
        val name: String,
        val email: String,
        val user_image_url: String?
    )
}