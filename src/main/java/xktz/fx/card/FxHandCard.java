package xktz.fx.card;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import xktz.fx.card.components.TypeIcon;
import xktz.fx.card.components.data.*;
import xktz.fx.hand.FxAllianceHand;
import xktz.fx.hand.FxHand;
import xktz.game.objects.card.HandCard;
import xktz.game.objects.card.soldier.SoldierCard;
import xktz.game.objects.card.strategy.StrategyCard;
import xktz.game.util.fx.ChoosingUtil;
import xktz.game.util.fx.FxUtil;
import xktz.game.util.fx.ImageUtil;
import xktz.game.util.game.StageUtil;
import xktz.game.util.language.LanguageUtil;

import java.io.CharArrayReader;
import java.rmi.RemoteException;
import java.util.Arrays;


public class FxHandCard extends Pane {

    private HandCard card;
    private FxHandCard popUpHandCard;
    private SoldierCard soldierCard = null;
    private StrategyCard strategyCard = null;

    private CostShape costShape;
    private BigAttackShape attackShape;
    private BigBloodShape bloodShape;
    private TypeIcon typeIcon;

    private ImageView imageView;

    private Label description;
    private Label name;

    private boolean createProgress = false;
    private boolean onDragging = false;

    private static final int WIDTH_HAND_CARD = 150;
    private static final int HEIGHT_LBL_NAME = 30;
    private static final double HEIGHT_PAINT = 93.4;
    private static final int HEIGHT_LBL_DESC = 70;

    private static final double Y_DESC = HEIGHT_LBL_NAME + HEIGHT_PAINT + 15;
    private static final Border BORDER = new Border(new BorderStroke(FxBattleCard.FX_BATTLE_CARD_BORDER_COLOR, BorderStrokeStyle.SOLID,
            new CornerRadii(5), new BorderWidths(5)));
    protected static final Background BACK_LBL_NAME = new Background(new BackgroundFill(Paint.valueOf("#868a79"), new CornerRadii(0), new Insets(0)));
    protected static final Paint PAINT_LBL_NAME = Paint.valueOf("#ffffff");
    private static Font FONT_NAME = Font.font("Microsoft YaHei", FontWeight.BOLD, 15);

    private static Font FONT_DESC = new Font(10);

    public FxHandCard(HandCard card) {
        this(card, true);
    }

    public FxHandCard(HandCard card, boolean needInitPopup) {
        this.setPrefWidth(WIDTH_HAND_CARD);
        this.setPrefHeight(200);
        this.card = card;
        if (card.getCard() instanceof SoldierCard) {
            soldierCard = (SoldierCard) card.getCard();
        } else {
            strategyCard = (StrategyCard) card.getCard();
        }
        if (needInitPopup) {
            initPopUp();
        }
        initUi();
    }

    private void initUi() {
        initData();
        initImageView();
        initName();
        initDesc();
        addAll();
    }

    /**
     * Init the image view
     */
    private void initImageView() {
        imageView = new ImageView();
        imageView.setImage(ImageUtil.getPaint(card.getName()));
        imageView.setFitWidth(WIDTH_HAND_CARD - 10);
        imageView.setFitHeight(HEIGHT_PAINT);
        imageView.setLayoutX(5);
        imageView.setLayoutY(HEIGHT_LBL_NAME+5);
    }


    private void initName() {
        name = new Label(LanguageUtil.getCardString(card.getName()));
        name.setPrefWidth(WIDTH_HAND_CARD-HEIGHT_LBL_NAME-5);
        name.setPrefHeight(HEIGHT_LBL_NAME);
        name.setLayoutX(HEIGHT_LBL_NAME);
        name.setLayoutY(5);
        name.setAlignment(Pos.CENTER);
        name.setFont(FONT_NAME);
        name.setBackground(BACK_LBL_NAME);
        name.setTextFill(PAINT_LBL_NAME);
    }

    private void initDesc() {
        description = new Label(LanguageUtil.getDescriptionString(card.getName()));
        description.setPrefWidth(WIDTH_HAND_CARD - 10);
        description.setPrefHeight(HEIGHT_LBL_DESC);
        description.setAlignment(Pos.TOP_CENTER);
        description.setTextAlignment(TextAlignment.JUSTIFY);
        description.setWrapText(true);
        description.setLayoutY(Y_DESC);
        description.setLayoutX(5);
        description.setFont(FONT_DESC);
    }


    /**
     * Init the data uis
     */
    private void initData() {
        initCost();
        if (soldierCard != null) {
            initAttack();
            initBlood();
            initTypeIcon();
        }
    }

    /**
     * Init the attack shape
     */
    private void initAttack() {
        this.attackShape = new BigAttackShape(card.getAttack());
        attackShape.setLayoutX(25);
        attackShape.setLayoutY(HEIGHT_LBL_NAME + HEIGHT_PAINT - 20);
    }

    /**
     * Init the blood shape
     */
    private void initBlood() {
        this.bloodShape = new BigBloodShape(card.getHp());
        bloodShape.setLayoutX(90);
        bloodShape.setLayoutY(HEIGHT_LBL_NAME + HEIGHT_PAINT - 20);
    }

    /**
     * Init the type icon
     */
    private void initTypeIcon() {
        this.typeIcon = new TypeIcon(soldierCard.getSoldierType());
        typeIcon.setLayoutX(55);
        typeIcon.setLayoutY(5);
    }

    /**
     * Init the cost shape
     */
    private void initCost() {
        if (soldierCard != null) {
            costShape = new CostShape(soldierCard.getCost(), soldierCard.getOriginalMoveCost());
        } else {
            costShape = new CostShape(strategyCard.getCost());
        }
        costShape.setLayoutX(5);
        costShape.setLayoutY(5);
    }

    /**
     * Add all
     */
    private synchronized void addAll() {
        this.getChildren().clear();
        this.setBackground(FxBattleCard.FX_BATTLE_CARD_BACKGROUND);
        this.setBorder(BORDER);
        getChildren().addAll(name, imageView, description, costShape);
        if (soldierCard != null) {
            getChildren().addAll(attackShape, bloodShape);
        }
    }

    private void clearAll() {
        this.setBackground(Background.EMPTY);
        this.setBorder(Border.EMPTY);
        this.getChildren().clear();
    }

    private void initPopUp() {
        popUpHandCard = new FxHandCard(card, false);
        initMoving();
        initDragging();
    }

    private void initMoving() {
        this.setOnMouseEntered((e) -> {
            if (FxUtil.getPanel().getChildren().contains(popUpHandCard) || onDragging) {
                return;
            }
            popUpHandCard.setLayoutX(this.localToScene(this.getBoundsInLocal()).getMinX());
            popUpHandCard.setLayoutY(this.getParent().getLayoutY() - 100);
            clearAll();
            createProgress = true;
            FxUtil.getPanel().getChildren().add(popUpHandCard);
        });
        this.setOnMouseExited((e) -> {
            if (onDragging) {
                return;
            }
            if (createProgress) {
                createProgress = false;
                return;
            }
            if (!this.isHover() && !popUpHandCard.isHover()) {
                FxUtil.getPanel().getChildren().remove(popUpHandCard);
                addAll();
            }
        });
        this.setOnMouseMoved((e) -> {
            if (onDragging) {
                return;
            }
            if (createProgress) {
                createProgress = false;
                return;
            }
            if (!this.isHover() && !popUpHandCard.isHover()) {
                FxUtil.getPanel().getChildren().remove(popUpHandCard);
                addAll();
            }
        });
        popUpHandCard.setOnMouseExited((e) -> {
            if (onDragging) {
                return;
            }
            if (createProgress) {
                createProgress = false;
                return;
            }
            if (!this.isHover() && !popUpHandCard.isHover()) {
                FxUtil.getPanel().getChildren().remove(popUpHandCard);
                addAll();
            }
        });
    }

    /**
     * Initing the dragging event
     */
    private void initDragging() {
        double[] refs = new double[]{0, 0};
        popUpHandCard.setOnMouseDragged((e) -> {
            if (refs[0] == 0 && refs[1] == 0) {
                refs[0] = e.getSceneX() - popUpHandCard.getLayoutX();
                refs[1] = e.getSceneY() - popUpHandCard.getLayoutY();
            }
            onDragging = true;
            // set
            popUpHandCard.setLayoutX(e.getSceneX() - refs[0]);
            popUpHandCard.setLayoutY(e.getSceneY() - refs[1]);
        });
        popUpHandCard.setOnMouseReleased((e) -> {
            if (FxUtil.getPanel().getChildren().contains(popUpHandCard)) {
                FxUtil.getPanel().getChildren().remove(popUpHandCard);
            }
            if (e.getSceneY() < this.getParent().getLayoutY()) {
                try {
                    card.use();
                } catch (RemoteException re) {
                    re.printStackTrace();
                    System.exit(1);
                }
            }
            this.onDragging = false;
            addAll();
        });
    }

    public HandCard getCard() {
        return card;
    }
}
