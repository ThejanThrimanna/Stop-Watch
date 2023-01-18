package com.mynextdeveloper_assignment.screen

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mynextdeveloper_assignment.core.App
import com.mynextdeveloper_assignment.viewmodel.MainViewModel
import com.mynextdeveloper_assignment.R
import com.mynextdeveloper_assignment.TimerDisplay
import com.mynextdeveloper_assignment.ui.theme.MyNextDeveloperAssignmentTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNextDeveloperAssignmentTheme {
                val mainViewModel: MainViewModel by viewModel()
                val timerState = mainViewModel.timerStateFlow.collectAsState()
                LaunchedEffect(Unit) {
                    mainViewModel.completed.collect {
                        showNotification(App.appContext)
                    }
                }
                TimerDisplay(timerState.value, mainViewModel::toggleStart)
            }
        }
    }
}

fun showNotification(context: Context) {
    val notifyIntent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    var notifyPendingIntent: PendingIntent? = null

    notifyPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.getBroadcast(
            context,
            1,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
    } else {
        PendingIntent.getBroadcast(
            context,
            1,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    val builder = NotificationCompat
        .Builder(context, "1")
        .setContentTitle(context.getString(R.string.timer))
        .setContentText(context.getString(R.string.completed))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(notifyPendingIntent)
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setAutoCancel(true)

    with(NotificationManagerCompat.from(context)) {
        notify(0, builder.build())
    }

}