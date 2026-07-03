package com.example.testapplicationidt.data

import kotlin.random.Random

class RandomStringGenerator(
    private val random: Random = Random.Default,
) {
    fun next(): String {
        val chars = "abcdefghijklmnopqrstuvwxyz0123456789"
        return (1..6)
            .map { chars[random.nextInt(chars.length)] }
            .joinToString("")
    }
}
