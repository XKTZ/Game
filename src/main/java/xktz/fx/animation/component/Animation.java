package xktz.fx.animation.component;

import javafx.scene.layout.Pane;
import xktz.fx.GamePanel;
import xktz.game.util.animation.AnimationFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Animation {
    private TimeNode[] timeNodes;
    private int[] differences;

    public void animate(Pane regionFrom, Pane regionTo) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < timeNodes.length; i++) {
            final int index = i;
            // check the kind of animation
            if (timeNodes[i].getAnimationComponent() instanceof SingleObjectAnimationComponent) {
                Pane regionEffect = timeNodes[i].getAnimationComponent().getInfluenceObject() == AnimationComponent.InfluenceObject.SELF?
                        regionFrom : regionTo;
                runAsynchronously(() -> timeNodes[index].getAnimationComponent().animateSingle(regionEffect, timeNodes[index].ref));
            } else if (timeNodes[i].getAnimationComponent() instanceof MultipleObjectAnimationComponent) {
                runAsynchronously(() -> timeNodes[index].getAnimationComponent().animateMutual(regionFrom, regionTo,
                        timeNodes[index].refStart, timeNodes[index].refEnd));
            } else {
                Pane regionEffect = timeNodes[i].getAnimationComponent().getInfluenceObject() == AnimationComponent.InfluenceObject.SELF?
                        regionFrom : regionTo;
                runAsynchronously(() -> timeNodes[index].getAnimationComponent().animateTransform(regionEffect));
            }
            // sleep
            try {
                Thread.sleep(differences[i]);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                System.exit(1);
            }
        }
    }

    public void runAsynchronously(Runnable runnable) {
        new Thread(runnable).start();
    }

    public void setTimeNodes(TimeNode[] timeNodes) {
        this.timeNodes = timeNodes;
        Arrays.sort(timeNodes, Comparator.comparingInt(TimeNode::getTimeStart));
        // init the differences
        initDifferences();
    }

    /**
     * Init the difference between two time nodes
     */
    private void initDifferences() {
        differences = new int[timeNodes.length];
        for (int i = 0; i < timeNodes.length; i++) {
            if (i == 0) {
                differences[0] = timeNodes[0].getTimeStart();
            } else {
                differences[i] = timeNodes[i].getTimeStart() - timeNodes[i - 1].getTimeStart();
            }
        }
    }

    public static class TimeNode {
        private AnimationComponent animationComponent;
        private String animationName;
        private int timeStart;
        private int[] refStart;
        private int[] refEnd;
        private int[] ref;

        public String getAnimationName() {
            return animationName;
        }

        public void setAnimationName(String animationName) {
            this.animationName = animationName;
            this.animationComponent = AnimationFactory.getAnimationComponent(animationName);
        }

        public AnimationComponent getAnimationComponent() {
            return animationComponent;
        }

        public void setAnimationComponent(AnimationComponent animationComponent) {
            this.animationComponent = animationComponent;
        }

        public int getTimeStart() {
            return timeStart;
        }

        public void setTimeStart(int timeStart) {
            this.timeStart = timeStart;
        }

        public int[] getRefStart() {
            return refStart;
        }

        public void setRefStart(int[] refStart) {
            this.refStart = refStart;
        }

        public int[] getRefEnd() {
            return refEnd;
        }

        public void setRefEnd(int[] refEnd) {
            this.refEnd = refEnd;
        }

        public int[] getRef() {
            return ref;
        }

        public void setRef(int[] ref) {
            this.ref = ref;
        }
    }
}
