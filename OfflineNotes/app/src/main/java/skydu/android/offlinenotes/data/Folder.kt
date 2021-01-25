package skydu.android.offlinenotes.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class Folder constructor (val id: Int, var judul: String): Parcelable