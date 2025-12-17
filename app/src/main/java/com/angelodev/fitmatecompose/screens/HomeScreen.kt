package com.angelodev.fitmatecompose.screens

import android.util.Log
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.angelodev.fitmatecompose.R
import com.angelodev.fitmatecompose.models.TaskModel
import com.angelodev.fitmatecompose.ui.theme.fitmateBeige
import com.angelodev.fitmatecompose.ui.theme.fitmateBlack
import com.angelodev.fitmatecompose.ui.theme.fitmateDarkTeal
import com.angelodev.fitmatecompose.ui.theme.fitmateGray
import com.angelodev.fitmatecompose.ui.theme.fitmateLightBlue
import com.angelodev.fitmatecompose.ui.theme.fitmateWhite
import com.angelodev.fitmatecompose.utilities.dialogs.AddTaskDialog
import com.angelodev.fitmatecompose.viewmodels.TasksViewModel
import timber.log.Timber
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
fun HomeScreen(
    viewModel: TasksViewModel = hiltViewModel()
) {

    var showDialog by remember { mutableStateOf(false) }
    val tasks by viewModel.tasksList.observeAsState(emptyList())

    LaunchedEffect(tasks) {
        val a = tasks
    }

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