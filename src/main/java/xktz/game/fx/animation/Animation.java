package xktz.game.fx.animation;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Animation {
    private String[] fromAnimation;
    private String[] toAnimation;
    private int timeDelay;
    private AnimationImage[] fromAnimationImages;
    private AnimationImage[] toAnimationImages;
    private int[] fromPoint;
    private int[] toPoint;

    public Animation() {
    }


    public String[] getFromAnimation() {
        return fromAnimation;
    }

    public void setFromAnimation(String[] fromAnimation) {
        fromAnimationImages = new AnimationImage[fromAnimation.length];
        for (int i = 0; i < fromAnimation.length; i++) {
            fromAnimationImages[i] = new AnimationImage(fromAnimation[i]);
        }
    }

    public String[] getToAnimation() {
        return toAnimation;
    }

    public void setToAnimation(String[] toAnimation) {
        this.toAnimation = toAnimation;
        toAnimationImages = new AnimationImage[toAnimation.length];
        for (int i = 0; i < toAnimation.length; i++) {
            toAnimationImages[i] = new AnimationImage(toAnimation[i]);
        }
    }

    public int getTimeDelay() {
        return timeDelay;
    }

    public void setTimeDelay(int timeDelay) {
        this.timeDelay = timeDelay;
    }

    public AnimationImage[] getFromAnimationImages() {
        return fromAnimationImages;
    }

    public void setFromAnimationImages(AnimationImage[] fromAnimationImages) {
        this.fromAnimationImages = fromAnimationImages;
    }

    public AnimationImage[] getToAnimationImages() {
        return toAnimationImages;
    }

    public void setToAnimationImages(AnimationImage[] toAnimationImages) {
        this.toAnimationImages = toAnimationImages;
    }

    public int[] getFromPoint() {
        return fromPoint;
    }

    public void setFromPoint(int[] fromPoint) {
        this.fromPoint = fromPoint;
    }

    public int[] getToPoint() {
        return toPoint;
    }

    public void setToPoint(int[] toPoint) {
        this.toPoint = toPoint;
    }

    public void animateFrom(Pane region) {
        Image[] images = this.getFromAnimationImages();
        int delay = this.getTimeDelay();
        int x = this.getFromPoint()[0], y = this.getFromPoint()[1];
        for (Image image : images) {
            ImageView imageView = new ImageView(image);
            region.getChildren().add(imageView);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                return;
            }
            region.getChildren().remove(imageView);
        }
    }
}
