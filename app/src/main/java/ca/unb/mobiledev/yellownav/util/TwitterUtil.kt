package ca.unb.mobiledev.yellownav.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import ca.unb.mobiledev.yellownav.model.Tweet
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.json.Json
import javax.json.JsonObject
import javax.json.stream.JsonParser

class TwitterUtil {
    private lateinit var tweets: ArrayList<Tweet>

    fun getTweets(mode: String): ArrayList<Tweet> {
        processJSON(mode)
        return tweets
    }

    private fun processJSON(mode: String) {
        // Initialize the data array
        tweets = ArrayList()

        // Process the JSON response from the URL
        val jsonString = loadJSONFromURL(mode)
        Log.i(TAG, jsonString!!)

        val jsonObj = JSONObject(jsonString)
        var jsonArry : JSONArray = JSONArray()
        try {
            jsonArry = jsonObj.getJSONArray("data")
        } catch (e: Exception) {
            return
        }

        for (i in 0 until (jsonArry).length()) {
            val tweet = Tweet()
            val obj = jsonArry.getJSONObject(i)
            tweet.text = obj.getString(JSON_KEY_TEXT)
            tweet.datetime = obj.getString(JSON_KEY_CREATED_AT)
            tweets.add(tweet)
        }
    }

    private fun loadJSONFromURL(mode: String): String? {
        val currentTime = Calendar.getInstance()

        val startOfToday = Calendar.getInstance()
        startOfToday.set(Calendar.HOUR_OF_DAY, 0)
        startOfToday.set(Calendar.MINUTE, 0)
        startOfToday.set(Calendar.SECOND, 0)
        /*

        val startOfDay = Calendar.getInstance()
        startOfDay.set(2022, 10, 30, 0, 0, 0)

        val noonOfDay = Calendar.getInstance()
        noonOfDay.set(2022, 10, 30, 12, 0, 0)

        val endOfDay = Calendar.getInstance()
        endOfDay.set(2022, 10, 30, 23, 59, 59)

         */

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        //val fmtStart = simpleDateFormat.format(startOfDay.time).toString() //demo
        val fmtStart = simpleDateFormat.format(startOfToday.time).toString()
        //val fmtEnd = simpleDateFormat.format(endOfDay.time).toString()

        Log.i(TAG, fmtStart)

        var connection: HttpURLConnection? = null
        var url: String? = null
        when (mode)
        {
            "alert" -> url = REQUEST_URL
            "map" -> url = REQUEST_URL_FOR_MAP
        }

        //connection = URL("$REQUEST_URL&start_time=$fmtStart&end_time=$fmtEnd").openConnection() as HttpURLConnection for demo
        connection = URL("$url&start_time=$fmtStart").openConnection() as HttpURLConnection
        //connection = URL(url).openConnection() as HttpURLConnection
        connection.addRequestProperty("Authorization", "Bearer $BEARER_TOKEN")
        val `in`: InputStream = BufferedInputStream(connection.inputStream)
        return convertStreamToString(`in`)
    }

    private fun convertStreamToString(`in`: InputStream): String {
        val data = StringBuilder()
        try {
            BufferedReader(InputStreamReader(`in`)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    data.append(line)
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "IOException")
        }
        return data.toString()
    }

    companion object {
        private const val JSON_KEY_TEXT = "text"
        private const val JSON_KEY_CREATED_AT = "created_at"
        private const val TAG = "TwitterUtil"
        private const val BASEURL = "https://api.twitter.com"
        private const val VERSION = "2"
        //private const val USER_ID = "1580554475022024704" // my dev account
        //private const val HASHTAG = "asdwalert"
        private const val PARKER_USER_ID = 942388459 // parker's twitter account
        private const val USER_ID = "964453050" // ASD_west
        private const val ENDPOINT = "users/$USER_ID/tweets"
        private const val ENDPOINT_FOR_MAP = "users/$PARKER_USER_ID/tweets"
        private const val QUERY = "exclude=replies,retweets&tweet.fields=created_at"
        private const val REQUEST_URL = "$BASEURL/$VERSION/$ENDPOINT?$QUERY"
        private const val REQUEST_URL_FOR_MAP = "$BASEURL/$VERSION/$ENDPOINT_FOR_MAP?$QUERY"
        private const val BEARER_TOKEN = "AAAAAAAAAAAAAAAAAAAAAJSFiAEAAAAAtgoGAsR1hqkLp1BnoTVGo5LfmUQ%3Djw0DyRfivouUbnhGZRMENYHMnteO9VJGkqqzedTJ4WOPqkxjNJ"
    }
}