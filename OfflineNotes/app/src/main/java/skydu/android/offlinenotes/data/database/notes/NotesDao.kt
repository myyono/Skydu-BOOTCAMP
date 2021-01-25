package skydu.android.offlinenotes.data.database.notes

import androidx.room.*

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes where folder_id = (:folder_id)")
    fun getAllByFolderId(folder_id: Int): List<NotesEntity>

    @Query("SELECT * FROM notes where id = (:id)")
    fun getById(id: Int): NotesEntity

    @Insert
    fun insert(entity: NotesEntity)

    @Update
    fun update(entity: NotesEntity)

    @Delete
    fun delete(entity: NotesEntity)

}