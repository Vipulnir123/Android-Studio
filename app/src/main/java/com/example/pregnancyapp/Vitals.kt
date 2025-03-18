package com.example.pregnancyapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "vitals_table")
data class Vitals(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: Date?,  // Make nullable
    val bloodPressure: String,
    val heartRate: Int,
    val weight: Float,
    val babyKicks: Int
)