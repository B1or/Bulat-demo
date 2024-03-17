package tech.droi.bulat_demo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartViewModel: ViewModel() {
    val slide = MutableLiveData(1)

    fun slide(slide: Int) {
        this.slide.value = slide
    }
}