package skydu.android.offlinenotes.data.database.notes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import skydu.android.offlinenotes.data.database.folder.FolderEntity

@Entity(
    tableName = "notes",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = FolderEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("folder_id"),
            onDelete = CASCADE
        )
    )
)
data class NotesEntity constructor(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "judul") var judul: String,
    @ColumnInfo(name = "isi") var isi: String,
    @ColumnInfo(name = "folder_id") var folderId: Int
)