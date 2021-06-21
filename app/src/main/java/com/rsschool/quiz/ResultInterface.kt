package com.rsschool.quiz

interface ResultInterface {

    fun reset(reset: Boolean)
    fun close()
    fun share(message: String)
}