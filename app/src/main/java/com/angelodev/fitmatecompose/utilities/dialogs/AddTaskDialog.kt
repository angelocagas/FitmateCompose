package com.angelodev.fitmatecompose.utilities.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.angelodev.fitmatecompose.models.TaskModel
import com.angelodev.fitmatecompose.ui.theme.fitmateBeige
import com.angelodev.fitmatecompose.ui.theme.fitmateBlack
import com.angelodev.fitmatecompose.ui.theme.fitmateDarkTeal
import com.angelodev.fitmatecompose.ui.theme.fitmateGray
import com.angelodev.fitmatecompose.ui.theme.fitmateWhite
import com.angelodev.fitmatecompose.viewmodels.TasksViewModel
import java.time.LocalDate

@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit,
    viewModel: TasksViewModel = hiltViewModel()
) {
    var taskTitle by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedHour by remember { mutableStateOf(12) }
    var selectedMinute by remember { mutableStateOf(0) }
    var selectedAmPm by remember { mutableStateOf("AM") }
    var showError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = fitmateBeige),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ADD NEW TASK",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = fitmateDarkTeal,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Title field
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Title",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = fitmateBlack
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = taskTitle,
                        onValueChange = { taskTitle = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(48.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = fitmateDarkTeal,
                            unfocusedBorderColor = fitmateGray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Date picker
                DatePickerSection(
                    selectedDate = selectedDate,
                    onDateChange = { selectedDate = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Time picker
                TimePickerSection(
                    selectedHour = selectedHour,
                    selectedMinute = selectedMinute,
                    selectedAmPm = selectedAmPm,
                    onHourChange = { selectedHour = it },
                    onMinuteChange = { selectedMinute = it },
                    onAmPmChange = { selectedAmPm = it }
                )

                if (showError) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Please fill in all fields",
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = fitmateWhite,
                            contentColor = fitmateDarkTeal
                        ),
                        border = BorderStroke(2.dp, fitmateDarkTeal),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "CANCEL",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = fitmateDarkTeal
                        )
                    }

                    Button(
                        onClick = {
                            if (taskTitle.isBlank()) {
                                showError = true
                            } else {
                                val formattedDate = selectedDate.format(
                                    java.time.format.DateTimeFormatter.ofPattern("MMMM d, yyyy")
                                )
                                val formattedTime = String.format(
                                    "%d:%02d %s",
                                    selectedHour,
                                    selectedMinute,
                                    selectedAmPm
                                )

                                val task = TaskModel(
                                    title = taskTitle,
                                    description = "Task1 desc",
                                    date = formattedDate,
                                    time = formattedTime
                                )
                                viewModel.addTask(task)
                                onDismiss()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = fitmateDarkTeal
                        ),
                        border = BorderStroke(2.dp, fitmateDarkTeal),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "SAVE",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = fitmateWhite
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DatePickerSection(
    selectedDate: LocalDate,
    onDateChange: (LocalDate) -> Unit
) {
    var currentMonth by remember { mutableStateOf(selectedDate) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Set Date",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = fitmateBlack
        )
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = fitmateWhite),
            border = BorderStroke(1.dp, fitmateGray)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Month/Year navigation
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val today = LocalDate.now()
                    val isCurrentMonth =
                        currentMonth.year == today.year && currentMonth.month == today.month

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Previous month",
                        tint = if (isCurrentMonth) fitmateGray else fitmateDarkTeal,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(enabled = !isCurrentMonth) {
                                currentMonth = currentMonth.minusMonths(1)
                            }
                    )

                    Text(
                        text = currentMonth.format(
                            java.time.format.DateTimeFormatter.ofPattern("MMM yyyy")
                        ),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = fitmateDarkTeal
                    )

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Next month",
                        tint = fitmateDarkTeal,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { currentMonth = currentMonth.plusMonths(1) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Weekday headers
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT").forEach { day ->
                        Text(
                            text = day,
                            fontSize = 12.sp,
                            color = fitmateGray,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Calendar grid
                CalendarGrid(
                    currentMonth = currentMonth,
                    selectedDate = selectedDate,
                    onDateSelected = onDateChange
                )
            }
        }
    }
}

@Composable
fun CalendarGrid(
    currentMonth: LocalDate,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val firstDayOfMonth = currentMonth.withDayOfMonth(1)
    val lastDayOfMonth = currentMonth.withDayOfMonth(currentMonth.lengthOfMonth())
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7

    val daysInMonth = currentMonth.lengthOfMonth()
    val weeks = (daysInMonth + firstDayOfWeek + 6) / 7

    Column {
        for (week in 0 until weeks) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (day in 0..6) {
                    val dayNumber = week * 7 + day - firstDayOfWeek + 1

                    if (dayNumber in 1..daysInMonth) {
                        val date = currentMonth.withDayOfMonth(dayNumber)
                        val isSelected = date == selectedDate
                        val isToday = date == LocalDate.now()
                        val isPast = date.isBefore(LocalDate.now())

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(1.dp)
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        isSelected -> fitmateDarkTeal
                                        isToday -> fitmateGray.copy(alpha = 0.3f)
                                        else -> Color.Transparent
                                    }
                                )
                                .clickable(enabled = !isPast) {
                                    onDateSelected(date)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = dayNumber.toString(),
                                fontSize = 14.sp,
                                color = when {
                                    isPast -> fitmateGray
                                    isSelected -> fitmateWhite
                                    else -> fitmateBlack
                                }
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun TimePickerSection(
    selectedHour: Int,
    selectedMinute: Int,
    selectedAmPm: String,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit,
    onAmPmChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Set Time",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = fitmateBlack
        )
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = fitmateWhite),
            border = BorderStroke(1.dp, fitmateGray)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Hour picker
                ScrollablePicker(
                    value = selectedHour,
                    range = 1..12,
                    onValueChange = onHourChange
                )

                Text(
                    text = ":",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = fitmateBlack
                )

                // Minute picker
                ScrollablePicker(
                    value = selectedMinute,
                    range = 0..59,
                    onValueChange = onMinuteChange,
                    formatter = { String.format("%02d", it) }
                )

                // AM/PM picker
                AmPmPicker(
                    selectedAmPm = selectedAmPm,
                    onAmPmChange = onAmPmChange
                )
            }
        }
    }
}

@Composable
fun ScrollablePicker(
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit,
    formatter: (Int) -> String = { it.toString() }
) {
    val listState = rememberLazyListState()
    var isUserScrolling by remember { mutableStateOf(false) }

    LaunchedEffect(value) {
        if (!isUserScrolling) {
            listState.animateScrollToItem(value - range.first)
        }
    }

    LaunchedEffect(listState.isScrollInProgress) {
        isUserScrolling = listState.isScrollInProgress

        if (!listState.isScrollInProgress && isUserScrolling) {
            snapshotFlow { listState.layoutInfo }
                .collect { layoutInfo ->
                    val centerY = layoutInfo.viewportEndOffset / 2

                    val centerItem = layoutInfo.visibleItemsInfo.minByOrNull { item ->
                        val itemCenter = item.offset + item.size / 2
                        kotlin.math.abs(itemCenter - centerY)
                    }

                    centerItem?.let {
                        val newValue = range.first + it.index
                        if (newValue in range) {
                            onValueChange(newValue)
                            listState.animateScrollToItem(it.index)
                        }
                    }
                }
        }
    }

    Box(
        modifier = Modifier.size(50.dp, 100.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 36.dp)
        ) {
            items(range.count()) { index ->
                val itemValue = range.first + index
                val isSelected = itemValue == value

                Text(
                    text = formatter(itemValue),
                    fontSize = if (isSelected) 28.sp else 18.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) fitmateBlack else fitmateGray,
                    modifier = Modifier
                        .height(40.dp)
                        .clickable {
                            onValueChange(itemValue)
                        }
                )
            }
        }
    }
}

@Composable
fun AmPmPicker(
    selectedAmPm: String,
    onAmPmChange: (String) -> Unit
) {
    val options = listOf("AM", "PM")
    val selectedIndex = if (selectedAmPm == "AM") 0 else 1
    val listState = rememberLazyListState()
    var isUserScrolling by remember { mutableStateOf(false) }

    LaunchedEffect(selectedIndex) {
        if (!isUserScrolling) {
            listState.animateScrollToItem(selectedIndex)
        }
    }

    LaunchedEffect(listState.isScrollInProgress) {
        isUserScrolling = listState.isScrollInProgress

        if (!listState.isScrollInProgress && isUserScrolling) {
            snapshotFlow { listState.layoutInfo }
                .collect { layoutInfo ->
                    val centerY = layoutInfo.viewportEndOffset / 2

                    val centerItem = layoutInfo.visibleItemsInfo.minByOrNull { item ->
                        val itemCenter = item.offset + item.size / 2
                        kotlin.math.abs(itemCenter - centerY)
                    }

                    centerItem?.let {
                        val newOption = options[it.index]
                        if (newOption != selectedAmPm) {
                            onAmPmChange(newOption)
                            listState.animateScrollToItem(it.index)
                        }
                    }
                }
        }
    }

    Box(
        modifier = Modifier.size(50.dp, 100.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 36.dp)
        ) {
            items(options.size) { index ->
                val option = options[index]
                val isSelected = option == selectedAmPm

                Text(
                    text = option,
                    fontSize = if (isSelected) 28.sp else 18.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) fitmateBlack else fitmateGray,
                    modifier = Modifier
                        .height(40.dp)
                        .clickable {
                            onAmPmChange(option)
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddTaskDialogPreview() {
    AddTaskDialog(onDismiss = {})
}