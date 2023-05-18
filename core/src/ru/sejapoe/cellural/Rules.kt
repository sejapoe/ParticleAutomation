package ru.sejapoe.cellural

import com.badlogic.gdx.graphics.Color
import kotlin.math.max

data class Rules(
    val types: Int,
    val colors: MutableList<Color>,
    val attraction: MutableList<MutableList<Double>>, // -1 - attraction, 0 - ignoring, 1 - propulsion
    val transitions: MutableList<Int>,
    val links: MutableList<Int>,
    val linksToType: MutableList<MutableList<Int>>,
    val percents: MutableList<Int>,
) {
    val randomType: Int
        get() {
            val sum = percents.sum()
            var random = (0..sum).random()
            for (i in 0 until types) {
                random -= percents[i]
                if (random <= 0) return i
            }
            return types - 1
        }
}

private val colors = listOf(
    Color.RED,
    Color.valueOf("c88c63"),
    Color.valueOf("51aa8d"),
    Color.CYAN,
    Color.WHITE,
    Color.MAGENTA,
)

val calmariRules = Rules(
    2,
    mutableListOf(
        Color.WHITE,
        Color.MAGENTA,
    ),
    mutableListOf(
        mutableListOf(-1.0, 1.0),
        mutableListOf(0.0, -1.0),
    ),
    mutableListOf(
        0, 0
    ),
    mutableListOf(
        2, 2,
    ),
    mutableListOf(
        mutableListOf(5, 1),
        mutableListOf(1, 0),
    ),
    mutableListOf(
        80, 20,
    )
)

val onigiriRules = Rules(
    3,
    mutableListOf(
        Color.RED,
        Color.valueOf("c88c63"),
        Color.valueOf("51aa8d")
    ),
    mutableListOf(
        mutableListOf(-1.0, -1.0, 1.0),
        mutableListOf(-1.0, -1.0, -1.0),
        mutableListOf(-1.0, -1.0, -1.0)
    ),
    mutableListOf(
        0, 1, 2,
    ),
    mutableListOf(1, 1, 2),
    mutableListOf(
        mutableListOf(0, 1, 1),
        mutableListOf(1, 2, 1),
        mutableListOf(1, 1, 2)
    ),
    mutableListOf(
        33, 33, 34
    )
)

fun randomRules(types: Int) = Rules(
    types,
    colors.subList(0, types).toMutableList(),
    MutableList(types) {
        MutableList(types) {
            (Math.random() * 3).toInt() - 1.0
        }
    },
    MutableList(types) {
        (Math.random() * types).toInt()
    },
    MutableList(types) {
        (Math.random() * 3).toInt() + 1
    },
    MutableList(types) {
        MutableList(types) {
            (Math.random() * 3).toInt()
        }
    }.symmetrical(),
    MutableList(types) {
        (Math.random() * 100).toInt()
    }
)

fun randomRules() = randomRules((Math.random() * 5 + 2).toInt())

fun MutableList<MutableList<Int>>.symmetrical() = MutableList(size) { i ->
    MutableList(size) { j ->
        max(this[i][j], this[j][i])
    }
}