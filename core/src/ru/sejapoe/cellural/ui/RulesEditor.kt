package ru.sejapoe.cellural.ui

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import ru.sejapoe.cellural.Rules
import ru.sejapoe.cellural.uiSkin
import kotlin.math.max


class RulesEditor(private var rules: Rules) : Table(uiSkin) {
    init {
        init()
    }

    private fun init() {
        add("Attraction")
        row()
        add(Table().apply {
            for (i in 0 until rules.types) {
                for (j in 0 until rules.types) {
                    val btn = TextButton(rules.attraction[i][j].toInt().toString(), uiSkin)
                    add(btn)
                    btn.addListener(object : ClickListener() {
                        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                            rules.attraction[i][j]++
                            if (rules.attraction[i][j] == 2.0) rules.attraction[i][j] = -1.0
                            btn.setText(rules.attraction[i][j].toInt().toString())
                            return super.touchDown(event, x, y, pointer, button)
                        }
                    })
                }
                row()
            }
        })
        row()
        add("Base links cnt")
        row()
        add(Table().apply {
            for (i in 0 until rules.types) {
                val btn = TextButton(rules.links[i].toString(), uiSkin)
                add(btn)
                btn.addListener(object : ClickListener() {
                    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                        val direction = if (button == Input.Buttons.LEFT) 1 else -1
                        rules.links[i] = max(0, rules.links[i] + direction)
                        btn.setText(rules.links[i].toString())
                        return super.touchDown(event, x, y, pointer, button)
                    }
                })
            }
        })
        row()
        add("Between links cnt")
        row()
        add(Table().apply {
            for (i in 0 until rules.types) {
                for (j in 0 until rules.types) {
                    val btn = TextButton(rules.linksToType[i][j].toInt().toString(), uiSkin)
                    add(btn)
                    btn.addListener(object : ClickListener() {
                        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                            val direction = if (button == Input.Buttons.LEFT) 1 else -1
                            rules.linksToType[i][j] = max(0, rules.linksToType[i][j] + direction)
                            btn.setText(rules.linksToType[i][j].toString())
                            return super.touchDown(event, x, y, pointer, button)
                        }
                    })
                }
                row()
            }
        })
        row()
        add("Transitions")
        row()
        add(Table().apply {
            for (i in 0 until rules.types) {
                val btn = TextButton(rules.transitions[i].toString(), uiSkin)
                add(btn)
                btn.addListener(object : ClickListener() {
                    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                        val direction = if (button == Input.Buttons.LEFT) 1 else -1
                        rules.transitions[i] = (rules.types + rules.transitions[i] + direction) % rules.types
                        btn.setText(rules.transitions[i].toString())
                        return super.touchDown(event, x, y, pointer, button)
                    }
                })
            }
        })
    }

    fun update(rules: Rules) {
        this.rules = rules
        clear()
        init()
    }
}