@file:JvmName("ImageUtil")

package xktz.game.util.fx

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import xktz.fx.card.FxBattleCard
import java.io.IOException
import javax.imageio.ImageIO
import kotlin.system.exitProcess


val HEAD_IMAGE_MAP: MutableMap<String, Image?> = mutableMapOf()
val PAINT_MAP: MutableMap<String, Image?> = mutableMapOf();
val ELLIPSE_MAP: MutableMap<Image, Image?> = mutableMapOf();

fun getHeadImage(name: String): Image? {
    if (HEAD_IMAGE_MAP.containsKey(name)) {
        return HEAD_IMAGE_MAP[name]
    } else {
        HEAD_IMAGE_MAP[name] = readHeadImage(name)
        return HEAD_IMAGE_MAP[name]
    }
}

fun readHeadImage(name: String): Image? {
    try {
        val image = ImageIO.read(FxBattleCard::class.java.getResource("/headimage/${name}.png"))
        return SwingFXUtils.toFXImage(image, null)
    } catch (ioe: IOException) {
        ioe.printStackTrace()
        exitProcess(1)
    }
}

fun getPaint(name: String): Image? {
    if (PAINT_MAP.containsKey(name)) {
        return PAINT_MAP[name];
    } else {
        PAINT_MAP[name] = readPaintImage(name);
        return PAINT_MAP[name];
    }
}

fun readPaintImage(name: String): Image? {
    try {
        val image = ImageIO.read(FxBattleCard::class.java.getResource("/paint/${name}.png"));
        return SwingFXUtils.toFXImage(image, null);
    } catch (ioe: IOException) {
        ioe.printStackTrace();
        exitProcess(1);
    }
}