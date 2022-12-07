package ca.unb.mobiledev.yellownav.ui.feed

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ca.unb.mobiledev.yellownav.databinding.FragmentFeedBinding
import ca.unb.mobiledev.yellownav.model.Tweet
import ca.unb.mobiledev.yellownav.util.TwitterUtil
import java.text.SimpleDateFormat
import java.util.concurrent.Executors

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val feedViewModel =
            ViewModelProvider(this).get(FeedViewModel::class.java)

        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var refreshBtn = binding.button as Button
        refreshBtn.setOnClickListener {
            getTweets()
            activity?.let {
                Toast.makeText(it, "Fetched latest info", Toast.LENGTH_LONG).show()
            }
        }

        getTweets()

        /*
        feedViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

         */
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getTweets() {
        Executors.newSingleThreadExecutor().execute {
            var tweets: ArrayList<Tweet> = ArrayList()
            var text = ""
            val handler = Handler(Looper.getMainLooper())
            tweets = TwitterUtil().getTweets("alert")
            text = ""

            for (tweet in tweets)
            {
                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000'Z'")
                //val formatter = SimpleDateFormat("MMM. dd, HH:mm")
                val formatter = SimpleDateFormat("KK:mm aa")
                val output: String = formatter.format(parser.parse(tweet.datetime))

                text += output + "\n"
                text += tweet.text?.replace("#asdwalert", "")
                    ?.replace("(", "")
                    ?.replace(")", "")
                text += "\n\n"
            }
            handler.post {
                val textView: TextView = binding.textFeed
                textView.text = text
            }
        }
    }
}