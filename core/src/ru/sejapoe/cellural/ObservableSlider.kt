package ru.sejapoe.cellural

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Slider
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener

class ObservableSlider(
    min: Float,
    max: Float,
    stepSize: Float,
    vertical: Boolean,
    skin: Skin,
    val observer: (Float) -> Unit
) :
    Slider(min, max, stepSize, vertical, skin) {
    init {
        addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                observer(value)
            }
        })
    }
}