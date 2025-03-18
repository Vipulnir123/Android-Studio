package com.example.pregnancyapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface VitalsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVitals(vitals: Vitals): Long

    @Query("SELECT * FROM vitals_table ORDER BY date DESC")
    fun getAllVitals(): Flow<List<Vitals>>
}