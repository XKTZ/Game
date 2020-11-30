package xktz.game.util.fx;

import javafx.application.Platform;
import org.omg.CORBA.PRIVATE_MEMBER;
import xktz.fx.GamePanel;
import xktz.fx.card.FxDescriptionCard;
import xktz.fx.card.FxInBattleDescriptionCard;
import xktz.game.objects.card.Card;

public class GameUtil {
    public static GamePanel PANEL_ALLIANCE;
    public static GamePanel PANEL_ENEMY;


    public static void showCardUse(Card card) {
        FxDescriptionCard descriptionCard = new FxDescriptionCard(card);
        descriptionCard.setLayoutX(30);
        descriptionCard.setLayoutY(FxInBattleDescriptionCard.POPUP_CARD_HEIGHT / 2 - 10);
        new Thread(() -> {
            Platform.runLater(() -> {
                PANEL_ALLIANCE.getChildren().add(descriptionCard);
            });
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            Platform.runLater(() -> {
                PANEL_ALLIANCE.getChildren().remove(descriptionCard);
            });
        }).start();
    }

    private static class ShowCardThread extends Thread {
        FxDescriptionCard descriptionCard;

        public ShowCardThread(Card card) {
            descriptionCard = new FxDescriptionCard(card);
            descriptionCard.setLayoutX(30);
            descriptionCard.setLayoutY(descriptionCard.getPrefHeight() / 2 - FxInBattleDescriptionCard.POPUP_CARD_HEIGHT / 2 - 10);
        }

        @Override
        public synchronized void start() {
            Platform.runLater(() -> {
                PANEL_ALLIANCE.getChildren().add(descriptionCard);
            });
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            Platform.runLater(() -> {
                PANEL_ALLIANCE.getChildren().remove(descriptionCard);
            });
        }
    }
}
