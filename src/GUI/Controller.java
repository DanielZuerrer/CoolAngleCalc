package GUI;

import Elements.AngleLine;
import Elements.Template;
import Elements.TemplateSide;
import Images.ImageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {

    private AngleLine redAngleLine = new AngleLine(61.8);
    private AngleLine greenAngleLine = new AngleLine(90);
    private Template template = new Template();
    private ImageController imageController = ImageController.getInstance();

    private Point dragDelta;
    private double rotateDelta = 0;
    private double rotateStart = 0;

    private BackgroundFill bgGreenYellow = new BackgroundFill(Color.GREENYELLOW, null, null);
    private BackgroundFill bgWhite = new BackgroundFill(Color.color(0.9568627451, 0.9568627451, 0.9568627451, 1.0), null, null);


    @FXML
    ImageView mainImageView;

    @FXML
    Pane templateImageViewPane;
    @FXML
    ImageView templateImageView;
    @FXML
    Pane greenLinePane;

    @FXML
    Line redLine;
    @FXML
    Line greenLine;
    @FXML
    Arc angleDisplay;
    @FXML
    Text angleDisplayLabel;

    @FXML
    TextFlow textR;
    @FXML
    TextFlow textL;

    @FXML
    public void initialize() {
        initTemplate();
        initAngleLines();

        templateImageViewPane.setOnMousePressed(event -> mousePressed(event));
        templateImageViewPane.setOnMouseDragged(event -> mouseDrag(event));

        greenLinePane.setOnMousePressed(event -> mousePressedGreen(event));
        greenLinePane.setOnMouseDragged(event -> mouseDragGreen(event));

    }

    private void initAngleLines() {
        redAngleLine.setLine(this.redLine);
        greenAngleLine.setLine(this.greenLine);
    }

    private void initTemplate() {
        templateImageView.setImage(template.getTemplateImage());
        textR.setBackground(new Background(bgGreenYellow));
    }

    public void setFirstImage(ActionEvent actionEvent) {
        File image1 = chooseFile("Select First Image");
        if (image1 != null) {
            imageController.setImage1(image1.getAbsolutePath());
            mainImageView.setImage(imageController.getImage1());

        }
    }

    public void setSecondImage(ActionEvent actionEvent) {
        File image2 = chooseFile("Select Second Image");
        if (image2 != null) {
            imageController.setImage2(image2.getAbsolutePath());
            mainImageView.setImage(imageController.getImage2());
        }
    }

    private File chooseFile(String windowTitle) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(windowTitle);
        return fileChooser.showOpenDialog(Main.getStage());
    }

    public void switchImages(ActionEvent actionEvent) {
        imageController.switchActiveImage();
        mainImageView.setImage(imageController.getActiveImage());
    }

    public void flipTemplate(ActionEvent actionEvent) {
        template.flip();
        templateImageView.setImage(template.getTemplateImage());

        redAngleLine.flip();
        adjustAngleDisplay();
        flipLetters();
    }

    public void mouseDrag(MouseEvent event) {
        if (event.isAltDown()) {
            rotate(event, 500, 400);
        } else {
            drag(event);
        }
    }

    public void mouseDragGreen(MouseEvent event) {
        if (event.isAltDown()) {
            rotate(event,30, 500);
        } else {
            drag(event);
        }
    }

    private void rotate(MouseEvent event, double w, double h) {
        Node node = (Node) event.getSource();
        double angleFromCenter = calculateRotationOffset(node, event, w,h);

        node.setRotate(rotateStart + rotateDelta - angleFromCenter);
        if (h < w) {
            redAngleLine.rotate(node.getRotate());
        } else {
            greenAngleLine.rotate(node.getRotate());
        }
        adjustAngleDisplay();
    }

    private void adjustAngleDisplay() {
        TemplateSide templateSide = template.getTemplateSide();
        double startAngle;
        double length;

        if (templateSide == TemplateSide.RIGHT) {
            startAngle = 180 - redLine.getRotate();
            length = 180 - greenLine.getRotate() + redLine.getRotate();
            length = length % 360;
            length = length < 0 ? (360 + length) : length;
        } else {
            startAngle = - greenLine.getRotate();
            length = 180 - (redLine.getRotate() - greenLine.getRotate());
            length = length % 360;
            length = length < 0 ? (360 + length) : length;
        }

        angleDisplay.setStartAngle(startAngle);
        angleDisplay.setLength(length);
        updateAngleDisplayLabel(length);
    }

    private void updateAngleDisplayLabel(double angle) {
        String angleString = String.format("%.2f", angle) + "°";
        angleDisplayLabel.setText(angleString);
    }
    private void flipLetters() {
        if (template.getTemplateSide() == TemplateSide.RIGHT) {
            textR.setBackground(new Background(bgGreenYellow));
            textL.setBackground(new Background(bgWhite));
        } else {
            textR.setBackground(new Background(bgWhite));
            textL.setBackground(new Background(bgGreenYellow));
        }
    }

    private void drag(MouseEvent event) {
        Node node = (Node) event.getSource();
        node.setLayoutX(event.getSceneX() - dragDelta.x);
        node.setLayoutY(event.getSceneY() - dragDelta.y);

        if (node.getLayoutX() < 50) {
            node.setLayoutX(50);
        } else if (node.getLayoutX() > 1200) {
            node.setLayoutX(1200);
        }

        if (node.getLayoutY() < -100) {
            node.setLayoutY(-100);
        } else if (node.getLayoutY() > 850) {
            node.setLayoutY(850);
        }


    }

    private void mousePressed(MouseEvent event) {
        Node node = (Node) event.getSource();
        double dragDeltaX = event.getSceneX() - node.getLayoutX();
        double dragDeltaY = event.getSceneY() - node.getLayoutY();
        dragDelta = new Point(dragDeltaX, dragDeltaY);

        rotateDelta = calculateRotationOffset(node, event, 500, 400);
        rotateStart = node.getRotate();
    }

    private void mousePressedGreen(MouseEvent event) {
        Node node = (Node) event.getSource();
        double dragDeltaX = event.getSceneX() - node.getLayoutX();
        double dragDeltaY = event.getSceneY() - node.getLayoutY();
        dragDelta = new Point(dragDeltaX, dragDeltaY);

        rotateDelta = calculateRotationOffset(node, event, 30, 500);
        rotateStart = node.getRotate();
    }

    private double calculateRotationOffset(Node node, MouseEvent event, double w, double h) {
        double templateWidth = w * node.getScaleX();
        double templateHeight = h * node.getScaleY();

        double centerX = node.getLayoutX() + templateWidth / 2;
        double centerY = node.getLayoutY() + templateHeight / 2;

        double deltaX = event.getSceneX() - centerX;
        double deltaY = event.getSceneY() - centerY;

        return Math.toDegrees(Math.atan2(deltaX, deltaY));
    }

    private class Point {
        private double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
