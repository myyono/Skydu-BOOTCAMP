package skydu.android.offlinenotes.data.database

import android.content.Context
import androidx.room.Room

object AppDatabaseHelper {
    private var INSTANCE: AppDatabase? = null
    private const val DB_NAME = "db_name"

    @Synchronized
    fun getInstance(context: Context): AppDatabase {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                .build()
        }
        return INSTANCE!!
    }
}