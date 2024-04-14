package com.example.tamagotchi
import android.widget.TextView
import android.content.Context
import androidx.work.WorkManager
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit


//MYWORKER-> TO CO SIE MA DZIAC TAM JAKAS FUNKCJA OD NAS !!!


class MyWorkManager(private val context: Context) {

    fun scheduleOneTimeWork() {
        /*val workRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)*/
    }

    fun schedulePeriodicWork() {
       /* val periodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 1, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "my_periodic_work",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )*/
    }

    fun cancelWork(tag: String) {
        WorkManager.getInstance(context).cancelAllWorkByTag(tag)
    }
}
