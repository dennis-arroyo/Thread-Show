/*
*
* Author:          Dennis O. Arroyo Rivera
* Student Number:  842-13-0585
* University:      University of Puerto Rico in Bayamon
*
*
* */


import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;

public class ThreadShow extends Application implements Runnable
{

    // Windows dimensions.
    public final double WIDTH = 750.00;
    public final double HEIGHT = 600.00;

    // Fixed number of games.
    private final int NUM_GAMES = 6;

    private final String TITLE = "Guess the Animal";
    private String currentAnimal = "";
    private String userInput = "";

    private int corrects = 0;

    private Button btn, btn2, btn3;
    private Image image;
    private ImageView imageView;
    private FileInputStream input;
    private TextField textField;
    private Group layout;
    private Scene scene;
    private ColorAdjust blackout;

    private int pos = 0;
    ArrayList<Integer> arr;

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle(TITLE);

        Label label = new Label("Type the name of the animal on the Text Field below.");

        label.setLayoutX(WIDTH/3 - 75);           // Position of label in X.
        label.setLayoutY(HEIGHT-550);             // Position of label in y.

        label.setTextFill(Color.INDIANRED);       // Text color set to INDIAN RED.
        label.setFont(Font.font(20));             // Font size set to 20.

        arr = new ArrayList<>();                  // Array List used to control images chosen for game

        for (int i = 0; i < 12; i++)              // Adds numbers 1 to n; n = number of images I have in
            arr.add(i);                           // my directory, which in this case is 12.

        Collections.shuffle(arr);                 // Shuffles the elements in my array list.

        btn = new Button(null);             // Button that will contain the images.

        btn.setLayoutX(WIDTH/2 - 50);            // Position of button (image) in X.
        btn.setLayoutY(HEIGHT - 500);            // Position of button (image) in Y.

        //--------------------------------------------------------------------------
        btn2 = new Button("Next");          // Next image button

        btn2.setOnMouseClicked(e ->
        {
            try
            {
                if (pos >= NUM_GAMES-1 )                      // Here we load the next image and evaluate the number of
                {                                             // times we have played, where 6 is the limit.
                    Results.display(corrects, NUM_GAMES);
                    stage.close();
                }
                imageLoader(++pos, -1.0);
            }

            catch (Exception e1)
            {
                e1.printStackTrace();
            }
        });


        btn2.setLayoutX(WIDTH - WIDTH/3 + 75);     // Position of button (Next) in X.
        btn2.setLayoutY(HEIGHT-30);               //  Position of button (Next) in Y.

        //--------------------------------------------------------------------------
        btn3 = new Button("Hint");

        btn3.setOnMouseClicked(e -> sound());      // Calls Sound() function when clicked <-------- THREAD

        btn3.setLayoutX(WIDTH/3 - 75);            // Position of button (Hint) in X.
        btn3.setLayoutY(HEIGHT-30);               // Position of button (Hint) in Y.


        imageLoader(pos, -1.0);        // Sets the first image as the program starts.

        //--------------------------------------------------------------------------
        TranslateTransition translateTransition = new TranslateTransition();

        // Sets the duration of the transition
        translateTransition.setDuration(Duration.millis(3000));

        // Sets the node (btn or my image) for the transition
        translateTransition.setNode(btn);

        // Setting the value of the transition along the x and y axis.
        translateTransition.setByX(100);
        translateTransition.setByY(150);

        // Setting the cycle count for the transition
        translateTransition.setCycleCount(1000);

        // Setting auto reverse value to true.. Moves back to original position.
        translateTransition.setAutoReverse(true);

        // Playing the animation
        translateTransition.play();
        //--------------------------------------------------------------------------

        //--------------------------------------------------------------------------
        textField = new TextField();                                // Text Field for User Input.
        textField.setPromptText("Enter the Name Here");             // Prompt Text in the background of the text field.
        textField.setLayoutX(WIDTH/2 - 75);                         // Position of Text Field in X.
        textField.setLayoutY(HEIGHT-30);                            // Position of Text Field in Y.
        textField.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)                      // Handles user input when enter is pressed on the
            {                                                       // Text Field.
                if (event.getCode().equals(KeyCode.ENTER))
                {
                    userInput = textField.getText().toString();
                    run();                                          // Calls the Thread's run method. <----- THREAD
                    textField.setText(null);                        // Erases text on the Text Field.
                }

            }
        });
        //--------------------------------------------------------------------------

        layout = new Group();                                    // layout that has all nodes (buttons and Text Field).
        layout.getChildren().addAll(label, btn, btn2, btn3, textField);

        scene = new Scene(layout, WIDTH, HEIGHT);               // Scene that contains my parent root (layout)

        scene.setFill(Color.SANDYBROWN);                        // Sets scene Background to Sandy Brown.

        stage.setScene(scene);                                  // Sets the scene to the stage.
        stage.setResizable(false);                              // Doesn't let user resize the stage (window).
        stage.show();                                           // Displays my stage (window) on screen.

        stage.setOnCloseRequest(e -> stage.close());            // Closes program appropriately when the 'x' is pressed.

    }

    // Thread's run() method.
    // Contains the programs logic.
    @Override
    public void run()
    {
        try

        {
            if (userInput.equals(currentAnimal))
            {
                imageLoader(pos, 0.1);
                corrects++;
            }

            else
                System.out.println("\nWrong Answer:");

            System.out.println("\tCurrent Animal: " + currentAnimal);
            System.out.println("\tUser Inout: " + userInput);
        }

        catch (Exception e) {}

    }

    // Loads the specified image with positions and sets its brightness.
    public void imageLoader(int pos, double brightness) throws Exception
    {
        switch (arr.get(pos))                 // assigns my path name and animal name according to the position I'm at.
        {
            case 0:
                currentAnimal = "rooster";
                break;
            case 1:
                currentAnimal = "cow";
                break;
            case 2:
                currentAnimal = "pig";
                break;
            case 3:
                currentAnimal = "monkey";
                break;
            case 4:
                currentAnimal = "sheep";
                break;
            case 5:
                currentAnimal = "dog";
                break;
            case 6:
                currentAnimal = "dolphin";
                break;
            case 7:
                currentAnimal = "elephant";
                break;
            case 8:
                currentAnimal = "cat";
                break;
            case 9:
                currentAnimal = "seal";
                break;
            case 10:
                currentAnimal = "horse";
                break;
            case 11:
                currentAnimal = "duck";
                break;
            case 12:
                currentAnimal = "lion";
                break;

        }

        input = new FileInputStream("images/" + currentAnimal + ".png");     // Used to open image file.
        image = new Image(input);

        imageView = new ImageView(image);
        imageView.setFitHeight(250);                                  // Sets the size of my image.
        imageView.setFitWidth(200);

        blackout = new ColorAdjust();
        blackout.setBrightness(brightness);                           // Sets the brightness, in this case the silloute
                                                                      // of the images.
        imageView.setEffect(blackout);

        btn.setGraphic(imageView);                                    // Assigns the image to the button.
        btn.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");      // Eliminates background
        btn.setMinWidth(80.0);                                        //
        btn.setPrefWidth(80.0);                                       // Sets all images to the same size.
        btn.setMaxWidth(80.0);                                        //
    }

    // Calls the Sound class to open the specified .wav file.
    public void sound()
    {
        Sound sound = new Sound("sounds/" + currentAnimal + ".wav");
        sound.start();
    }

    // Launches the start() method.
    public static void main(String[] args)
    {
        launch(args);
    }
}
