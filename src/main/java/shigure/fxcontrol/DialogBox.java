package shigure.fxcontrol;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

public class DialogBox extends HBox {
    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/bobu_40x40.png"));
    private final Image mikiImage = new Image(this.getClass().getResourceAsStream("/images/fuzuki_40x40.png"));
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    public DialogBox(String text, Pov pov) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            dialog.setText(text);
            displayPicture.setClip(
                    new Circle(displayPicture.getX() + displayPicture.getFitWidth() / 2,
                            displayPicture.getY() + displayPicture.getFitHeight() / 2,
                            20));
            if (pov == Pov.MIKI) {
                displayPicture.setImage(mikiImage);
                flip();
            } else if (pov == Pov.USER) {
                displayPicture.setImage(userImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void flip() {
        ObservableList<Node> items = FXCollections.observableArrayList(getChildren());
        Collections.reverse(items);
        getChildren().setAll(items);
        setAlignment(Pos.CENTER_LEFT);
    }

    public enum Pov {
        MIKI,
        USER
    }
}
