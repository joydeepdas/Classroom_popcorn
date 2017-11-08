package com.ClassroomPopcorn.main.template;

import com.ClassroomPopcorn.main.windows.movie.movieOnline;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class recommendedMovie {

    public static BorderPane movieTemplate(String movieName, int yearOfRelease, String genre, double IMDB, int likes, int downloads, String description, String director, String cast, String movieLink, String trailerLink, String movieImageURL){

        BorderPane movies = new BorderPane();
        movies.setStyle("-fx-border-color: black");

        Image img = new Image(movieImageURL);
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(140);
        imgView.setPreserveRatio(true);
        movies.setLeft(imgView);

        movies.setOnMouseClicked(e-> {
            e.consume();
            movieOnline ob = new movieOnline();
            ob.movieOnline(movieName, yearOfRelease, genre, IMDB, likes, downloads, description, director, cast, movieLink, trailerLink, movieImageURL);
        });

        movies.setCursor(Cursor.HAND);
        return movies;
    }
}
