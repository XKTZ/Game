package xktz.fx.animation.component;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class SingleObjectAnimationComponent implements AnimationComponent{
    private String[] animation;
    private int timeDelay;
    private AnimationComponentImage[] animationComponentImages;

    public SingleObjectAnimationComponent() {
    }



    public void setAnimation(String[] animation) {
        animationComponentImages = new AnimationComponentImage[animation.length];
        for (int i = 0; i < animation.length; i++) {
            animationComponentImages[i] = new AnimationComponentImage(animation[i]);
        }
    }

    public String[] getAnimation() {
        return animation;
    }

    public int getTimeDelay() {
        return timeDelay;
    }

    public void setTimeDelay(int timeDelay) {
        this.timeDelay = timeDelay;
    }

    public AnimationComponentImage[] getAnimationComponentImages() {
        return animationComponentImages;
    }

    public void setAnimationComponentImages(AnimationComponentImage[] animationComponentImages) {
        this.animationComponentImages = animationComponentImages;
    }

    @Override
    public void animateSingle(Pane region, int[] point) {
        Thread thread = new Thread(() -> {
            Image[] images = this.getAnimationComponentImages();
            int delay = this.getTimeDelay();
            int x = point[0], y = point[1];
            ImageView imageView = new ImageView();
            imageView.setLayoutX(x);
            imageView.setLayoutY(y);
            for (Image image : images) {
                imageView.setImage(image);
                Platform.runLater(() -> {
                    region.getChildren().add(imageView);
                });
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                    return;
                }
                Platform.runLater(() -> {
                    region.getChildren().remove(imageView);
                });
            }
        });
        thread.start();
    }
}
