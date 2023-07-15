package com.game.frodojourney.app.character


import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.DpOffset
import com.game.frodojourney.character.CharacterTurned

class CharacterBody private constructor(
    val torso: BodyPart.Torso,
    val rightLeg: BodyPart.RightLeg,
    val leftLeg: BodyPart.LeftLeg,
    val rightArm: BodyPart.RightArm,
    val leftArm: BodyPart.LeftArm,
    val centerX: Offset = setOf(torso, leftLeg, leftArm).let { parts ->
        val min = parts.minBy { it.positionStart.x }.positionStart.x
        val max = parts.maxBy { it.positionEnd.x }.positionEnd.x
        return@let Offset((min + max) / 2f, 0f)
    }
) {
    companion object {
        fun create(
            torsoBitmap: ImageBitmap?,
            legBitmap: ImageBitmap?,
            armBitmap: ImageBitmap?,
            armPit: Offset,
            groin: Offset
        ): CharacterBody? {
            return if (torsoBitmap != null && legBitmap != null && armBitmap != null) {
                val torso = BodyPart.Torso(torsoBitmap, armPit = armPit, groin = groin)
                CharacterBody(
                    torso = torso,
                    rightLeg = BodyPart.RightLeg(legBitmap, torso),
                    leftLeg = BodyPart.LeftLeg(legBitmap, torso),
                    rightArm = BodyPart.RightArm(armBitmap, torso),
                    leftArm = BodyPart.LeftArm(armBitmap, torso)
                )
            } else null
        }
    }

    fun DrawScope.drawBody(offset: Offset, turn: CharacterTurned) {
        withTransform({
            scale(scaleX = turn.mirrorX, scaleY = 1f, pivot = centerX)
            translate(left = offset.x, top = offset.y)
        }) {
            drawImage(
                torso.bitmap,
                torso.positionStart
            )
            drawImage(
                rightLeg.bitmap,
                rightLeg.positionStart
            )
            drawImage(
                leftLeg.bitmap,
                leftLeg.positionStart
            )
            drawImage(
                rightArm.bitmap,
                rightArm.positionStart
            )
            drawImage(
                leftArm.bitmap,
                leftArm.positionStart
            )
        }
    }
}

interface BodyPartWithParent {
    val parent: BodyPart
}

sealed class BodyPart {
    abstract val bitmap: ImageBitmap
    abstract val positionStart: Offset
    abstract val positionEnd: Offset

    data class Torso(
        override val bitmap: ImageBitmap,
        override val positionStart: Offset = Offset(0f, 0f),
        override val positionEnd: Offset = Offset(bitmap.width.toFloat(), bitmap.height.toFloat()),
        val armPit: Offset,
        val groin: Offset
    ) : BodyPart()

    data class RightLeg(
        override val bitmap: ImageBitmap,
        override val parent: Torso,
        override val positionStart: Offset = parent.groin,
        override val positionEnd: Offset = Offset(
            parent.positionEnd.x,
            parent.positionEnd.y + bitmap.height.toFloat()
        )
    ) : BodyPart(), BodyPartWithParent

    data class LeftLeg(
        override val bitmap: ImageBitmap,
        override val parent: Torso,
        override val positionStart: Offset = parent.groin,
        override val positionEnd: Offset = Offset(
            parent.positionEnd.x,
            parent.positionEnd.y + bitmap.height.toFloat()
        )

    ) : BodyPart(), BodyPartWithParent

    data class LeftArm(
        override val bitmap: ImageBitmap,
        override val parent: Torso,
        override val positionStart: Offset = parent.armPit,
        override val positionEnd: Offset = Offset(
            parent.positionStart.x,
            parent.positionStart.y + bitmap.height.toFloat()
        )

    ) : BodyPart(), BodyPartWithParent

    data class RightArm(
        override val bitmap: ImageBitmap,
        override val parent: Torso,
        override val positionStart: Offset = parent.armPit,
        override val positionEnd: Offset = Offset(
            parent.positionStart.x,
            parent.positionStart.y + bitmap.height.toFloat()
        )

    ) : BodyPart(), BodyPartWithParent
}

interface GenericCharacter {
    val position: DpOffset
    val turned: CharacterTurned

    fun DrawScope.draw()
}