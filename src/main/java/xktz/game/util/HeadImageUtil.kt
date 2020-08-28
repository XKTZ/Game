@file:JvmName("HeadImageUtil")
package xktz.game.util

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import xktz.game.fx.Card
import java.io.IOException
import javax.imageio.ImageIO
import kotlin.system.exitProcess



val IMAGE_MAP: MutableMap<String, Image?> = mutableMapOf<String, Image?>()


fun getHeadImage(name: String): Image? {
    if (IMAGE_MAP.containsKey(name)) {
        return IMAGE_MAP[name]
    } else {
        IMAGE_MAP[name]= readImage(name)
        return IMAGE_MAP[name]
    }
}

fun readImage(name: String): Image? {
    try {
        val image = ImageIO.read(Card::class.java.getResource("/image/${name}.png"))
        return SwingFXUtils.toFXImage(image, null)
    } catch (ioe: IOException) {
        ioe.printStackTrace()
        exitProcess(1)
    }
}