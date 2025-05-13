package com.example.finalproject

class PethomeModel {

    private var isDraggingCapybara = false
    private var isPetting = false
    var name: String = ""
    var happiness: Int = 1000

    fun updatePetDown() {
        isDraggingCapybara = true
        isPetting = true
    }

    fun isPetting() : Boolean {
        return isPetting
    }

    fun isDragging() : Boolean {
        return isDraggingCapybara
    }

    fun increaseHappiness() {
        happiness = (happiness + 2).coerceAtMost(1000)
    }

    fun endPetting() {
        isDraggingCapybara = false
        isPetting = false
    }
}