package skydu.android.instaclone.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    
    fun convertTimeFormat(date: String): String {
        val dateFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault()
        )
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        val millis = dateFormat.parse(date).time

        return SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault()).format(millis)
    }
}