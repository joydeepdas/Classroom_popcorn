package com.ClassroomPopcorn.main.windows.home;

import com.ClassroomPopcorn.main.functions.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class main extends Application {
    public static Stage window;
    public BorderPane layout;
    public static Scene scene;

    public static void main(String args[])
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window=primaryStage;
        window.setTitle("Classroom Popcorn");

        ScrollPane mainScreen = new ScrollPane();
        scene = new Scene(mainScreen,800,500);

        BorderPane topLayout = header.header();
        BorderPane centerLayout = centerSearch.centerSearch();

        layout = new BorderPane();
        layout.setTop(topLayout);
        layout.setCenter(centerLayout);

        mainScreen.setContent(layout);
        mainScreen.setFitToWidth(true);
        mainScreen.setFitToHeight(true);

        window.setScene(scene);

        scene.getStylesheets().add(main.class.getResource("../../resources/css/main.css").toExternalForm());
        window.getIcons().add(new Image(getClass().getResourceAsStream("../../resources/images/ClassroomPopcorn.png")));

        window.setMinWidth(800);
        window.setMinHeight(500);
        window.show();
        window.setOnCloseRequest(e->{System.exit(0);});

    }
}