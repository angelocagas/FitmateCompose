package com.angelodev.fitmatecompose.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.angelodev.fitmatecompose.R
import com.angelodev.fitmatecompose.models.TaskModel
import com.angelodev.fitmatecompose.ui.theme.fitmateBeige
import com.angelodev.fitmatecompose.ui.theme.fitmateBlack
import com.angelodev.fitmatecompose.ui.theme.fitmateDarkTeal
import com.angelodev.fitmatecompose.ui.theme.fitmateGray
import com.angelodev.fitmatecompose.ui.theme.fitmateLightBlue
import com.angelodev.fitmatecompose.ui.theme.fitmateWhite
import com.angelodev.fitmatecompose.viewmodels.TasksViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


data class FitmateTask(
    val title: String,
    val time: String,
    val description: String
)

val upcomingTasks = listOf(
    FitmateTask(
        "MORNING EXERCISE",
        "06:28AM",
        "Take of your body. It's the only place you have to live."
    ),
    FitmateTask("BADMINTON", "02:35PM", "Take of your body. It's the only place you have to live."),
    FitmateTask("Cry", "02:35PM", "Take of your body. It's the only place you have to live.")
)

val tomorrowTasks = listOf(
    FitmateTask(
        "MORNING JOG",
        "05:00AM",
        "Take of your body. It's the only place you have to live."
    ),
    FitmateTask("ZUMBA", "04:00PM", "Take of your body. It's the only place you have to live."),
    FitmateTask(
        "SECRET TASK",
        "04:00PM",
        "Take of your body. It's the only place you have to live."
    ),
)

data class FitmateDate(
    val dayOfMonth: String,
    val dayOfWeek: String,
    val isSelected: Boolean = false
)


@Composable
fun HomeScreen() {

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                shape = CircleShape,
                containerColor = fitmateDarkTeal,
                contentColor = fitmateWhite,
                modifier = Modifier.size(72.dp)
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add Task",
                    modifier = Modifier.size(36.dp)
                )
            }
        },
        containerColor = fitmateBeige
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Header()
            Spacer(modifier = Modifier.height(12.dp))
            DatePicker()
            Spacer(modifier = Modifier.height(24.dp))
            TasksSection("UPCOMING TODAY", upcomingTasks)
            Spacer(modifier = Modifier.height(24.dp))
            TasksSection("TASKS FOR TOMORROW", tomorrowTasks)
            Spacer(modifier = Modifier.height(12.dp))
        }

        if (showDialog) {
            AddTaskDialog(onDismiss = { showDialog = false })
        }
    }
}

@Composable
fun AddTaskDialog(onDismiss: () -> Unit) {
    var taskTitle by remember { mutableStateOf("") }
    var selectedHour by remember { mutableStateOf(6) }
    var selectedMinute by remember { mutableStateOf(28) }
    var selectedPeriod by remember { mutableStateOf("PM") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = fitmateWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ADD NEW TASK",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = fitmateBlack,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // To do field
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "To do *",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = fitmateBlack
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = taskTitle,
                        onValueChange = { taskTitle = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = fitmateDarkTeal,
                            unfocusedBorderColor = fitmateGray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Set Date and Time section
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Set Date and Time",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = fitmateBlack
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = "Set Date",
                        onValueChange = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                Icons.Filled.KeyboardArrowDown,
                                contentDescription = "Select Date",
                                tint = fitmateDarkTeal
                            )
                        },
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

                // Time Picker
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Hour
                    Text(
                        text = String.format("%02d", selectedHour),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = fitmateBlack
                    )

                    Spacer(modifier = Modifier.width(16.dp))
                    Text(":", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = fitmateBlack)
                    Spacer(modifier = Modifier.width(16.dp))

                    // Minute
                    Text(
                        text = String.format("%02d", selectedMinute),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = fitmateBlack
                    )

                    Spacer(modifier = Modifier.width(16.dp))
                    Text(":", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = fitmateBlack)
                    Spacer(modifier = Modifier.width(16.dp))

                    // AM/PM
                    Text(
                        text = selectedPeriod,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = fitmateBlack
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = fitmateDarkTeal
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Cancel",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = fitmateWhite
                        )
                    }

                    Button(
                        onClick = {
                            // TODO: Save task
                            onDismiss()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = fitmateWhite,
                            contentColor = fitmateDarkTeal
                        ),
                        border = BorderStroke(2.dp, fitmateDarkTeal),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Save",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = fitmateDarkTeal
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Header(viewModel: TasksViewModel = hiltViewModel()) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.fitmate_logo),
            contentDescription = "Fitmate Logo",
            modifier = Modifier.size(120.dp, 80.dp),
            contentScale = ContentScale.Fit
        )
        Icon(
            Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Add Task",
            tint = fitmateDarkTeal,
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    // Create and add the task as specified
                    val task = TaskModel(
                        title = "Task1",
                        description = "Task1 desc",
                        date = "December 11, 2025",
                        time = "3:00 PM"
                    )
                    viewModel.addTask(task)
                }
        )
    }
}

@Composable
fun DatePicker() {
    val today = LocalDate.now()
    val dates = getNextDates(7)

    val monthYear = today.month.getDisplayName(
        TextStyle.FULL,
        Locale.ENGLISH
    ).uppercase() + " ${today.year}"

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = monthYear,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = fitmateDarkTeal
            )
            Icon(
                Icons.Filled.KeyboardArrowDown,
                contentDescription = "Select month",
                tint = fitmateDarkTeal
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(dates) { date ->
                DateCard(date)
            }
        }
    }
}

@Composable
fun DateCard(date: FitmateDate) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (date.isSelected) fitmateDarkTeal else fitmateWhite
        ),
        border = BorderStroke(1.dp, fitmateDarkTeal),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = date.dayOfMonth,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = if (date.isSelected) fitmateWhite else fitmateDarkTeal
            )
            Text(
                text = date.dayOfWeek,
                fontSize = 16.sp,
                color = if (date.isSelected) fitmateWhite else fitmateDarkTeal
            )
        }
    }
}

@Composable
fun TasksSection(title: String, tasks: List<FitmateTask>) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = fitmateDarkTeal
            )
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(fitmateDarkTeal)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(tasks) { task ->
                TaskCard(task)
            }
        }
    }
}

@Composable
fun TaskCard(task: FitmateTask) {
    Box(
        modifier = Modifier
            .width(220.dp)
            .padding(start = 8.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = fitmateLightBlue),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = task.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = fitmateBlack
                )
                Text(text = task.time, fontSize = 14.sp, color = fitmateDarkTeal)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = task.description, fontSize = 14.sp, color = fitmateGray)
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(containerColor = fitmateDarkTeal),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text("Delete", fontSize = 12.sp, color = fitmateWhite)
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = fitmateWhite,
                            contentColor = fitmateDarkTeal
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text("Edit", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

fun getNextDates(count: Int = 5): List<FitmateDate> {
    val today = LocalDate.now()

    return (0 until count).map { index ->
        val date = today.plusDays(index.toLong())
        FitmateDate(
            dayOfMonth = date.dayOfMonth.toString(),
            dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                .uppercase(),
            isSelected = index == 0
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}