package duke;
import duke.helper.Parser;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Duke duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/qiyu.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/longjuan.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void greet() {
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog("Nice to meet you !", dukeImage)
        );
    }
    public void setDuke(Duke d) {
        duke = d;
        greet();
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        String response = duke.getResponse(input);
        if (Parser.isExit(input)) {
            dialogContainer.getChildren().addAll(
                    DialogBox.getDukeDialog("Bye, see you again", dukeImage)
            );
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            Stage stage = (Stage) dialogContainer.getScene().getWindow();
            pause.setOnFinished(event ->
                    stage.close()
            );
            pause.play();
            return;
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();

    }
}