package com.example.pregnancyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pregnancyapp.ui.theme.PregnancyAppTheme

class MainActivity : ComponentActivity() {
    private val viewModel: VitalsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PregnancyAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen(viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: VitalsViewModel) {
    val vitalsList by viewModel.allVitals.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pregnancy Vitals", fontSize = 20.sp) },
                actions = {
                    IconButton(onClick = { /* Handle notification click */ }) {
                        Icon(Icons.Filled.Notifications, contentDescription = "Notifications")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Vitals")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFFEECE8)) // Light pink background from Figma
                .padding(16.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(vitalsList) { vitals ->
                    VitalsCard(vitals)
                }
            }
        }
    }

    if (showDialog) {
        AddVitalsDialog(onDismiss = { showDialog = false }, viewModel)
    }
}

@Composable
fun VitalsCard(vitals: Vitals) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Blood Pressure: ", fontSize = 16.sp)
                Text("${vitals.bloodPressure}", fontSize = 16.sp, color = Color(0xFFD32F2F)) // Red BP value
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Heart Rate: ", fontSize = 16.sp)
                Text("${vitals.heartRate} bpm", fontSize = 16.sp, color = Color(0xFF1976D2)) // Blue HR value
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Weight: ", fontSize = 16.sp)
                Text("${vitals.weight} kg", fontSize = 16.sp, color = Color(0xFF388E3C)) // Green Weight
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Baby Kicks: ", fontSize = 16.sp)
                Text("${vitals.babyKicks}", fontSize = 16.sp, color = Color(0xFF6A1B9A)) // Purple Kicks
            }
        }
    }
}