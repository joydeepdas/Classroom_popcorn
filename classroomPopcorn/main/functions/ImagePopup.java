package com.ClassroomPopcorn.main.windows.movie;

import java.io.File;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.stage.*;

/**
 * Example to show an image popup with an
 * autoplaying audio file.
 * @author Mr. Davis
 */
public class ImagePopup extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {

        Button btn = new Button();
        btn.setText("Click to show Image Popup");
        btn.setOnAction(e -> imagePopupWindowShow(""));

        BorderPane pane = new BorderPane();
        pane.setCenter(btn);

        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Image Popup Example");
        primaryStage.show();
    }

    /**
     * This method will be the image popup.
     */
    public void imagePopupWindowShow(String path) {
        // All of our necessary variables

        File imageFile;
        Image image;
        ImageView imageView;
        BorderPane pane;
        Scene scene;
        Stage stage;


        image = new Image(path);
        imageView = new ImageView(image);

        pane = new BorderPane();
        pane.setCenter(imageView);
        scene = new Scene(pane);

        stage = new Stage();
        stage.setScene(scene);
        stage.setMaxHeight(400);
        stage.setMaxWidth(400);

        stage.setOnCloseRequest(
                e -> {
                    e.consume();
                    stage.close();
                }
        );

        stage.showAndWait();





    }
}