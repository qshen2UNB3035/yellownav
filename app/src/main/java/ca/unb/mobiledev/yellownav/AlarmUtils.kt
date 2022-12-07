package ca.unb.mobiledev.yellownav

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*

class AlarmUtils(context: Context) {
    private var mContext = context
    private var alarmMgr: AlarmManager? = null
    private var alarmIntent: PendingIntent

    init {
        alarmMgr = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(mContext, AlarmReceiver::class.java).let { mIntent ->
            PendingIntent.getBroadcast(mContext, 100, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    fun initRepeatingAlarm(){
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            Log.e("Test", timeInMillis.toString())

        }

        alarmMgr?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            1669977000000,
            86400000,
            alarmIntent
        )
        alarmMgr?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            1670007600000,
            86400000,
            alarmIntent
        )
    }

}