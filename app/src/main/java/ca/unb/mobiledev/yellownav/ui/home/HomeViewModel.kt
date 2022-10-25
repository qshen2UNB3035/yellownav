package ca.unb.mobiledev.yellownav.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Nov 4:\n" +
                "Bus#2 is delayed.\n" +
                "\n" +
                "Nov 20:\n" +
                "Bus#6 is delayed.\n" +
                "Bus#7 is delayed.\n" +
                "\n" +
                "Nov 24:\n" +
                "Due to snow storm, all buses are delayed.\n"

    }
    val text: LiveData<String> = _text
}