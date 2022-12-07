package ca.unb.mobiledev.yellownav.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Toggle Bus Tracking Notifications"
    }
    val text: LiveData<String> = _text
}