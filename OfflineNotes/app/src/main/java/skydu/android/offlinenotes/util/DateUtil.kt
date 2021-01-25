package skydu.android.offlinenotes.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun convertToString(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
        val strDate: String = dateFormat.format(date)
        return strDate
    }
}