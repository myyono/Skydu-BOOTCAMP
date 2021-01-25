package skydu.android.offlinenotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import skydu.android.offlinenotes.data.database.folder.FolderDao
import skydu.android.offlinenotes.data.database.folder.FolderEntity
import skydu.android.offlinenotes.data.database.notes.NotesDao
import skydu.android.offlinenotes.data.database.notes.NotesEntity

@Database(entities = arrayOf(FolderEntity::class, NotesEntity::class), version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun folderDao(): FolderDao

    abstract fun notesDao(): NotesDao

}