package com.example.rickmorty.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDialog(
    currentStatus: String?,
    currentSpecies: String?,
    onDismiss: () -> Unit,
    onApply: (String?, String?) -> Unit
) {
    var selectedStatus by remember { mutableStateOf(currentStatus) }
    var selectedSpecies by remember { mutableStateOf(currentSpecies) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Filter Characters") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Status Filter
                Column {
                    Text(
                        text = "Status",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = selectedStatus == "Alive",
                            onClick = { selectedStatus = if (selectedStatus == "Alive") null else "Alive" },
                            label = { Text("Alive") }
                        )
                        FilterChip(
                            selected = selectedStatus == "Dead",
                            onClick = { selectedStatus = if (selectedStatus == "Dead") null else "Dead" },
                            label = { Text("Dead") }
                        )
                        FilterChip(
                            selected = selectedStatus == "unknown",
                            onClick = { selectedStatus = if (selectedStatus == "unknown") null else "unknown" },
                            label = { Text("Unknown") }
                        )
                    }
                }

                // Species Filter
                Column {
                    Text(
                        text = "Species",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = selectedSpecies == "Human",
                            onClick = { selectedSpecies = if (selectedSpecies == "Human") null else "Human" },
                            label = { Text("Human") }
                        )
                        FilterChip(
                            selected = selectedSpecies == "Alien",
                            onClick = { selectedSpecies = if (selectedSpecies == "Alien") null else "Alien" },
                            label = { Text("Alien") }
                        )
                        FilterChip(
                            selected = selectedSpecies == "Robot",
                            onClick = { selectedSpecies = if (selectedSpecies == "Robot") null else "Robot" },
                            label = { Text("Robot") }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onApply(selectedStatus, selectedSpecies) }
            ) {
                Text("Apply")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
} 