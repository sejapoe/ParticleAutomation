package ru.sejapoe.cellural

import kotlin.math.pow

class Particle(
    var x: Double,
    var y: Double,
    var vx: Double,
    var vy: Double,
    var type: Int,
    val radius: Double,
    val bonds: MutableSet<Particle> = mutableSetOf()
) {
    fun sqrDistTo(it: Particle) = (it.x - x).pow(2.0) + (it.y - y).pow(2.0)

    fun copy() = Particle(x, y, vx, vy, type, radius, bonds)
}