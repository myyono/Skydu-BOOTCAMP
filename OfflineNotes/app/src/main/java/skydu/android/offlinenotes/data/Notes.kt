package skydu.android.offlinenotes.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Notes constructor (val id: Int, var judul: String, var isi: String): Parcelable