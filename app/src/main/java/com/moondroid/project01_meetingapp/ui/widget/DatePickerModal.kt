package com.moondroid.project01_meetingapp.ui.widget

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    initialDate: String? = null,
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val calendar = GregorianCalendar()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = convertDateToMillis(initialDate),
        yearRange = 1900..calendar.get(Calendar.YEAR)
    )
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                if (datePickerState.selectedDateMillis != null) {
                    onDateSelected(convertMillisToDate(datePickerState.selectedDateMillis!!))
                } else {
                    onDateSelected("1990.01.01")
                }
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

fun convertDateToMillis(date: String?): Long {
    val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    val newDate = if (date.isNullOrEmpty()) "1990.01.01" else date
    val mills = formatter.parse(newDate) ?: Date(System.currentTimeMillis())
    return mills.time + 60 * 60 * 24 * 1000
}


fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    return formatter.format(Date(millis))
}
