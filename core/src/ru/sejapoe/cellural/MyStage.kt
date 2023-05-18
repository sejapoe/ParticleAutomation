package ru.sejapoe.cellural

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.Viewport
import ru.sejapoe.cellural.ui.Ui
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class MyStage(viewport: Viewport) : Stage(viewport) {
    var speed = Constants.SPEED
    var radius = 3.0
    var h = viewport.worldHeight
    var w = viewport.worldWidth
    var particleCount = 1000
    lateinit var particles: Array<Particle>
    val links = mutableSetOf<Link>()
    var rules = calmariRules // randomRules()

    private val shapeRenderer = ShapeRenderer()
    private val ui = Ui(this)

    init {
        addActor(ui)
        randomizeParticles()
    }

    override fun draw() {
        super.draw()
        shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color.DARK_GRAY
        particles.forEach {
            shapeRenderer.color = rules.colors[it.type]
            shapeRenderer.circle(it.x.toFloat(), it.y.toFloat(), it.radius.toFloat())
        }
        shapeRenderer.color = Color.WHITE
        links.forEach {
            shapeRenderer.line(it.a.x.toFloat(), it.a.y.toFloat(), it.b.x.toFloat(), it.b.y.toFloat())
        }
        shapeRenderer.end()
    }

    fun update() {
        particles.forEach { it.move() }
        particles.indices.forEach { i ->
            ((i + 1) until particles.size).forEach { j ->
                interact(particles[i], particles[j])
            }
        }
    }

    private fun Particle.move() {
        x += vx
        y += vy
        vx *= Constants.AIR_FRICTION
        vy *= Constants.AIR_FRICTION
        borderRepulsion()
        transition()
    }

    private fun Particle.transition() {
        val type = rules.transitions[this.type] ?: return
        if (type == this.type) return
        if (bonds.size == rules.links[this.type]) {
            this.type = type
            bonds.forEach {
                it.bonds.remove(this)
                links.remove(Link(this, it))
                links.remove(Link(it, this))
            }
            bonds.clear()
        }
    }

    private fun Particle.borderRepulsion() {
        if (x <= Constants.BORDER) {
            vx += speed * 0.05
            if (x < 0) {
                x = -x
                vx *= -0.5
            }
        }
        if (y <= Constants.BORDER) {
            vy += speed * 0.05
            if (y < 0) {
                y = -y
                vy *= -0.5
            }
        }
        if (x >= w - Constants.BORDER) {
            vx -= speed * 0.05
            if (x > w) {
                x = w * 2 - x
                vx *= -0.5
            }
        }
        if (y >= h - Constants.BORDER) {
            vy -= speed * 0.05
            if (y > h) {
                y = h * 2 - y
                vy *= -0.5
            }
        }
    }

    private fun interact(a: Particle, b: Particle) {
        if (a === b) return
        a forcedBy b
        b forcedBy a
//        creation(a, b)
        link(a, b)
    }

    private fun link(a: Particle, b: Particle) {
        val dist = a.sqrDistTo(b)
        if (dist < radius.pow(2.0) * Constants.LINK_SIZE && !a.bonds.contains(b)) {
            if (rules.linksToType[a.type][b.type] > a.bonds.count { it.type == b.type }
                && rules.linksToType[b.type][a.type] > b.bonds.count { it.type == a.type }
                && rules.links[a.type] > a.bonds.size
                && rules.links[b.type] > b.bonds.size) {
                a.bonds.add(b)
                b.bonds.add(a)
                links.add(Link(a, b))
            }
        }
        if (dist > radius.pow(2.0) * Constants.LINK_SIZE * 4 && a.bonds.contains(b)) {
            a.bonds.remove(b)
            b.bonds.remove(a)
            links.remove(Link(a, b))
            links.remove(Link(b, a))
        }
    }

    private infix fun Particle.forcedBy(with: Particle) {
        var dist = sqrDistTo(with)
        var a = rules.attraction[type][with.type] / dist
//        if (dist > 100.0.pow(2.0)) return
        if (dist < 1) dist = 1.0
        a = when {
            dist < radius * radius * Constants.LINK_SIZE -> {
                1.0 / dist
            }

            bonds.contains(with) -> {
                Constants.LINK_FORCE
            }

            else -> {
                a
            }
        }
        applyAcceleration(with, a)
    }

    private fun Particle.applyAcceleration(with: Particle, a: Double) {
        val angle = atan2(y - with.y, x - with.x)
        vx += cos(angle) * a * speed
        vy += sin(angle) * a * speed
    }

    fun randomizeParticles() {
        particles = Array(particleCount) {
            // random type using rules.percents
            Particle(
                x = Math.random() * w,
                y = Math.random() * h,
                vx = Math.random() * 2 - 1,
                vy = Math.random() * 2 - 1,
                type = rules.randomType,
                radius = radius
            )
        }
    }

    override fun dispose() {
        super.dispose()
        shapeRenderer.dispose()
    }
}