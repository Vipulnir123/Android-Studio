package com.example.pregnancyapp

import kotlinx.coroutines.flow.Flow

class VitalsRepository(private val vitalsDao: VitalsDao) {
    val allVitals: Flow<List<Vitals>> = vitalsDao.getAllVitals()

    suspend fun insertVitals(vitals: Vitals) {
        vitalsDao.insertVitals(vitals)
    }
}
