package com.example.pregnancyapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pregnancyapp.Vitals
import com.example.pregnancyapp.VitalsViewModel
import java.util.Date

@Composable
fun AddVitalsDialog(onDismiss: () -> Unit, viewModel: VitalsViewModel) {
    var bloodPressure by remember { mutableStateOf("") }
    var heartRate by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var babyKicks by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Vitals") },
        text = {
            Column {
                OutlinedTextField(value = bloodPressure, onValueChange = { bloodPressure = it }, label = { Text("Blood Pressure") })
                OutlinedTextField(value = heartRate, onValueChange = { heartRate = it }, label = { Text("Heart Rate") })
                OutlinedTextField(value = weight, onValueChange = { weight = it }, label = { Text("Weight") })
                OutlinedTextField(value = babyKicks, onValueChange = { babyKicks = it }, label = { Text("Baby Kicks") })
            }
        },
        confirmButton = {
            Button(onClick = {
                // Create a Vitals object with the current date
                val vitals = Vitals(
                    date = Date(), // Use java.util.Date instead of LocalDate
                    bloodPressure = bloodPressure,
                    heartRate = heartRate.toIntOrNull() ?: 0,
                    weight = weight.toFloatOrNull() ?: 0f,
                    babyKicks = babyKicks.toIntOrNull() ?: 0
                )

                // Pass the Vitals object to the ViewModel
                viewModel.addVitals(
                    bloodPressure = vitals.bloodPressure,
                    heartRate = vitals.heartRate,
                    weight = vitals.weight,
                    babyKicks = vitals.babyKicks
                )
                onDismiss()
            }) {
                Text("Save")
            }
        }
    )
}