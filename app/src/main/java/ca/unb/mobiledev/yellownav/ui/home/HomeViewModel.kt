package ca.unb.mobiledev.yellownav.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "I'm here for pickup!"
    }
    val text: LiveData<String> = _text
}