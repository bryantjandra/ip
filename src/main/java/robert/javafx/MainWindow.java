package robert.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import robert.Robert;

/**
 * Controller for the main application window of the Robert chatbot.
 * Manages the layout, initialization, and user interactions.
 */
public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Robert robert;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image robertImage = new Image(this.getClass().getResourceAsStream("/images/Robert.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Initializes the Robert chatbot to this controller and shows the welcome message immediately.
     *
     * @param r The Robert chatbot instance.
     */
    public void setRobert(Robert r) {
        assert r != null : "Robert instance cannot be null for MainWindow";
        this.robert = r;
        String startup = robert.getStartupMessage();
        dialogContainer.getChildren().add(
                DialogBox.getRobertDialog(startup, robertImage)
        );
    }

    /**
     * Handles user input from the text field or "Send" button.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = robert.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getRobertDialog(response, robertImage)
        );

        userInput.clear();
    }
}
