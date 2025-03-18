package com.example.pregnancyapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date  // ✅ Add this missing import
import java.util.concurrent.TimeUnit

class VitalsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: VitalsRepository

    // Using StateFlow instead of LiveData for Compose
    private val _allVitals = MutableStateFlow<List<Vitals>>(emptyList())
    val allVitals: StateFlow<List<Vitals>> = _allVitals.asStateFlow()

    init {
        val vitalsDao = VitalsDatabase.getDatabase(application).vitalsDao()
        repository = VitalsRepository(vitalsDao)

        viewModelScope.launch {
            repository.allVitals.collect { vitalsList ->
                _allVitals.value = vitalsList
            }
        }

        scheduleVitalsReminder()
    }

    fun addVitals(bloodPressure: String, heartRate: Int, weight: Float, babyKicks: Int) =
        viewModelScope.launch {
            val vitals = Vitals(
                date = Date(),  // ✅ This will now work
                bloodPressure = bloodPressure,
                heartRate = heartRate,
                weight = weight,
                babyKicks = babyKicks
            )
            repository.insertVitals(vitals)
        }

    private fun scheduleVitalsReminder() {
        val workRequest = PeriodicWorkRequestBuilder<VitalsReminderWorker>(5, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .build()

        WorkManager.getInstance(getApplication()).enqueueUniquePeriodicWork(
            "vitals_reminder",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
