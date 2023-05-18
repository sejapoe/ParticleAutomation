package ru.sejapoe.cellural.ui

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import ru.sejapoe.cellural.MyStage
import ru.sejapoe.cellural.ObservableSlider
import ru.sejapoe.cellural.randomRules
import ru.sejapoe.cellural.uiSkin

class Ui(val stage: MyStage) : Table(uiSkin) {
    private val radiusSlider = ObservableSlider(1.0f, 10.0f, 1.0f, false, uiSkin) {
        stage.radius = it.toDouble()
        restart()
    }

    private val countSlider = ObservableSlider(2.0f, 1000.0f, 30.0f, false, uiSkin) {
        stage.particleCount = it.toInt()
        restart()
    }

    private val speedSlider = ObservableSlider(0.0f, 30.0f, 1.0f, false, uiSkin) {
        stage.speed = it.toDouble()
    }

    private val rulesBtn = TextButton("Random rules", uiSkin)
    private val restartBtn = TextButton("Restart", uiSkin)
    private val rulesEditor = RulesEditor(stage.rules)

    init {
        radiusSlider.value = stage.radius.toFloat()
        countSlider.value = stage.particleCount.toFloat()
        speedSlider.value = stage.speed.toFloat()
        rulesBtn.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                stage.rules = randomRules()
                rulesEditor.update(stage.rules)
                restart()
                return super.touchDown(event, x, y, pointer, button)
            }
        })
        restartBtn.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                restart()
                return super.touchDown(event, x, y, pointer, button)
            }
        })

        setFillParent(true)
        left()
        top()

        add("Radius")
        row()
        add(radiusSlider)
        row()
        add("Count")
        row()
        add(countSlider)
        row()
        add(rulesEditor)
        row()
        add("Speed")
        row()
        add(speedSlider)
        row()
        add(rulesBtn)
        add(restartBtn)
    }

    private fun restart() {
        stage.links.clear()
        stage.randomizeParticles()
    }
}