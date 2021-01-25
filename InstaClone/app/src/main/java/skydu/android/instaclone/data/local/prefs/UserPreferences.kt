package skydu.android.instaclone.data.local.prefs

import android.content.Context
import skydu.android.instaclone.CustomApplication

class UserPreferences {

    companion object {
        const val KEY_USER_USERNAME = "PREF_KEY_USERNAME"
        const val KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
    }

    val prefs = CustomApplication.application.getSharedPreferences(
        "bootcamp-instagram-project-prefs",
        Context.MODE_PRIVATE
    )

    fun getUserName(): String? =
        prefs.getString(KEY_USER_USERNAME, null)

    fun setUserName(userName: String) =
        prefs.edit().putString(KEY_USER_USERNAME, userName).apply()

    fun removeUserName() =
        prefs.edit().remove(KEY_USER_USERNAME).apply()

    fun getAccessToken(): String? =
        prefs.getString(KEY_ACCESS_TOKEN, null)

    fun setAccessToken(token: String) =
        prefs.edit().putString(KEY_ACCESS_TOKEN, token).apply()

    fun removeAccessToken() =
        prefs.edit().remove(KEY_ACCESS_TOKEN).apply()
}