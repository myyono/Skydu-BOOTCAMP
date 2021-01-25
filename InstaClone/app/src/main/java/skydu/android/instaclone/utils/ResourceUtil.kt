package skydu.android.instaclone.utils

import skydu.android.instaclone.CustomApplication

object ResourceUtil {
    fun getString(resourceId: Int): String {
        return CustomApplication.application.resources.getString(resourceId)
    }
}
