package ca.unb.mobiledev.yellownav.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "I'm here for pickup!"
    }
    val text: LiveData<String> = _text
}