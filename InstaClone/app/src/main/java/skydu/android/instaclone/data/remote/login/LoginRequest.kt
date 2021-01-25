package skydu.android.instaclone.data.remote.login

data class LoginRequest(
    var username: String,
    var password: String,
    var device_name: String = "Dumnmy"

)