import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Dennis Arroyo on 1/17/2018.
 */
public class Results
{

    public static void display(int corrects, int numGames)
    {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Results");
        stage.setMinWidth(400);

        Label label = new Label("Correct Answers: " + corrects +
                        "\n\nTotal Games: " + numGames);

        label.setTextFill(Color.INDIANRED);
        label.setFont(Font.font(20));

        Button btn = new Button("Exit");
        btn.setOnMouseClicked(e -> stage.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, btn);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.setFill(Color.SANDYBROWN);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
