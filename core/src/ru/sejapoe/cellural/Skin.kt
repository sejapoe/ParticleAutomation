package ru.sejapoe.cellural

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Skin

val uiSkin by lazy {
    Skin(Gdx.files.internal("uiskin.json"))
}
