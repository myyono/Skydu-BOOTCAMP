package skydu.android.offlinenotes.data.database.folder

import androidx.room.*

@Dao
interface FolderDao {
    @Query("SELECT * FROM folder")
    fun getAll(): List<FolderEntity>

    @Query("SELECT * FROM folder where id = (:id)")
    fun getById(id: Int): FolderEntity

    @Insert
    fun insert(entity: FolderEntity)

    @Update
    fun update(entity: FolderEntity)

    @Delete
    fun delete(entity: FolderEntity)

}