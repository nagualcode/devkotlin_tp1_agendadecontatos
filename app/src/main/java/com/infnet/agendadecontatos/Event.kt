package com.infnet.agendadecontatos



// exemplo: https://www.raywenderlich.com/10391019-livedata-tutorial-for-android-deep-dive

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set


    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}