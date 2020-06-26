package xktz.game.objects.player;

import xktz.game.objects.card.HandCard;
import xktz.game.serializable.SerializableLinkedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CardStack {

    private LinkedList<HandCard> handCards;

    public CardStack(HandCard[] handCardIn) {
        handCards = new SerializableLinkedList<>();
        // disruption this array
        disruption(handCardIn);
        // add into hand cards
        handCards.addAll(Arrays.asList(handCardIn));
    }

    private void disruption(HandCard[] handCardIn) {
        for (int i = handCardIn.length; i > 0; i--) {
            // get a random number between 0 - i
            int indexRandom = (int) (Math.random() * i);
            // if index random is the i, -=1
            if (indexRandom == i) {
                indexRandom -= 1;
            }
            // transfer two cards
            swap(handCardIn, i-1, indexRandom);
        }
    }

    private void swap(HandCard[] list, int index1, int index2) {
        HandCard temp = list[index1];
        list[index1] = list[index2];
        list[index2] = temp;
    }

    /**
     * @return the card draw
     * Draw one card
     */
    public HandCard drawCard() {
        if (handCards.isEmpty()) {
            return null;
        }
        // draw one card
        HandCard card = handCards.getLast();
        // remove this card
        handCards.removeLast();
        // return
        return card;
    }
}
