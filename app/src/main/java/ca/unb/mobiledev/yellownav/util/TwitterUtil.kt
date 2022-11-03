package ca.unb.mobiledev.yellownav.util

import android.icu.number.Notation.simple
import android.util.Log
import ca.unb.mobiledev.yellownav.model.Tweet
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.ArrayList

import javax.json.Json
import javax.json.stream.JsonParser

class TwitterUtil {
    private lateinit var tweets: ArrayList<Tweet>

    fun getTweets(): ArrayList<Tweet> {
        return tweets
    }

    private fun processJSON() {
        Log.i("Lab6", "processing started")
        // Initialize the data array
        tweets = ArrayList()

        // Process the JSON response from the URL
        val jsonString = loadJSONFromURL()
        Log.i(TAG, jsonString!!)
        try {
            val parser = Json.createParser(StringReader(jsonString))

            while (parser.hasNext()) {
                when (parser.next()) {
                    JsonParser.Event.VALUE_STRING -> {
                        val tweet = Tweet()
                        tweet.text = parser.string
                        tweets.add(tweet)
                    }
                    else -> {}
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadJSONFromURL(): String? {
        var connection: HttpURLConnection? = null
        connection = URL(REQUEST_URL).openConnection() as HttpURLConnection
        connection.addRequestProperty("Authorization", BEARER_TOKEN)
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

    // Initializer to read our data source (JSON file) into an array of course objects
    init {
        processJSON()
    }

    companion object {
        private const val TAG = "TwitterUtil"
        private const val BASEURL = "https://api.twitter.com"
        private const val VERSION = "2"
        private const val USER_ID = "1580554475022024704"
        private const val ENDPOINT = "users/$USER_ID/tweets"
        private const val REQUEST_URL = "$BASEURL/$VERSION/$ENDPOINT"
        private const val BEARER_TOKEN = "AAAAAAAAAAAAAAAAAAAAAJSFiAEAAAAAtgoGAsR1hqkLp1BnoTVGo5LfmUQ%3Djw0DyRfivouUbnhGZRMENYHMnteO9VJGkqqzedTJ4WOPqkxjNJ"
    }
}