package com.example.huellitas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.huellitas.model.Mascota

@Database(entities = [Mascota::class], version = 1, exportSchema = false)
abstract class HuellitasDatabase : RoomDatabase() {

    abstract fun mascotaDao(): MascotaDao

    companion object {
        @Volatile
        private var INSTANCE: HuellitasDatabase? = null

        fun getDatabase(context: Context): HuellitasDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HuellitasDatabase::class.java,
                    "huellitas_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}