package com.avaneesh.gridpro.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun GridSelector(onGridSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val gridOptions = listOf("Rule of Thirds", "Golden Ratio", "Golden Spiral", "Diagonal", "Triangle", "Center", "Custom")

    Box(modifier = Modifier) {
        Button(onClick = { expanded = true }) {
            Text("Select Grid")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            gridOptions.forEach { grid ->
                DropdownMenuItem(
                    onClick = {
                        onGridSelected(grid)
                        expanded = false
                    },
                    text = { Text(grid) }
                )
            }
        }
    }
}
