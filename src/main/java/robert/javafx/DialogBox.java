package robert.javafx;

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

/**
 * Represents a dialog box consisting of an ImageView and a Label.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Creates a DialogBox with the specified text and image.
     */
    private DialogBox(String text, Image img) {
        assert text != null : "DialogBox text cannot be null";
        assert img != null : "DialogBox image cannot be null";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
        Circle clip = new Circle(displayPicture.getFitWidth() / 2,
                displayPicture.getFitHeight() / 2,
                displayPicture.getFitWidth() / 2);
        displayPicture.setClip(clip);

    }

    /**
     * Flips the dialog box such that the ImageView is on the left side.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.dialog.getStyleClass().add("user-label");
        return db;
    }


    public static DialogBox getRobertDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.dialog.getStyleClass().add("robert-label");
        db.flip();
        return db;
    }


    public static DialogBox getErrorDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        db.dialog.getStyleClass().add("error-label");
        return db;
    }

}
