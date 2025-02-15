package robert.javafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import robert.Robert;

/**
 * A GUI for the Robert chatbot using FXML.
 */
public class Main extends Application {

    private Robert robert = new Robert("data/tasks.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Robert Chatbot");
            stage.setResizable(true);

            MainWindow controller = fxmlLoader.<MainWindow>getController();
            controller.setRobert(robert);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
