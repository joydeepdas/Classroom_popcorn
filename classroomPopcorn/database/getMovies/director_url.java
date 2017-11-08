package com.ClassroomPopcorn.main.windows.movie;

import com.ClassroomPopcorn.main.*;

import javafx.scene.Cursor;
import javafx.stage.Popup;

import com.ClassroomPopcorn.database.getMovies.recommendedMoviesDetail;
import com.ClassroomPopcorn.database.utils.DBUtils;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class director_url {

    public void director_url(String image_url) {
        Stage movieWindow = new Stage();
        movieWindow.setTitle("Cast_Info!!");

        ScrollPane details = new ScrollPane();
        details.setFitToWidth(true);

        WebView webview = new WebView();
        webview.getEngine().load(image_url);

        BorderPane mainMovie = new BorderPane(webview);
        details.setContent(mainMovie);
        Scene scene = new Scene(details);
        scene.getStylesheets().add(director_url.class.getResource("../../resources/css/main.css").toExternalForm());
        movieWindow.setScene(scene);

        movieWindow.setMaximized(true);
        movieWindow.show();

        movieWindow.widthProperty().addListener(e -> webview.setPrefWidth(movieWindow.getWidth()));

        movieWindow.setMinWidth(800);
        movieWindow.setMinHeight(600);
        webview.setPrefHeight(0.9 * movieWindow.getHeight());

        movieWindow.getIcons().add(new Image(getClass().getResourceAsStream("../../resources/images/ClassroomPopcorn.png")));

        movieWindow.setOnCloseRequest(e -> {
            webview.getEngine().load(null);
            //trailerview.getEngine().load(null);
        });


    }

}