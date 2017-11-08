package com.ClassroomPopcorn.database.getMovies;

import com.ClassroomPopcorn.database.utils.DBUtils;
import com.ClassroomPopcorn.main.template.recommendedMovie;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class recommendedMoviesDetail {

    public static BorderPane movies(String userId){
        BorderPane movieResults = new BorderPane();
        movieResults.setPadding(new Insets(0,20,0,0));

        VBox verticalTemplates = new VBox(10);
        verticalTemplates.setAlignment(Pos.TOP_LEFT);

        Label searchLabel = new Label("Recommended Movies ");
        searchLabel.setFont(new Font("Cambria", 20));
        searchLabel.setTextFill(Color.web("#5a5a5a"));

        verticalTemplates.getChildren().addAll(searchLabel);

        HBox horizontalBoxFirst = new HBox(20);
        HBox horizontalBoxSecond = new HBox(20);

        Connection conn = null;
        PreparedStatement stmtt = null;
        ResultSet rs = null;

        String query = DBUtils.prepareSelectQuery("", "classroompopcorn.recomendedmovies", "userId = "+userId);

        int count = 0;
        try {
            conn = DBUtils.getConnection();
            stmtt = conn.prepareStatement(query);
            rs = stmtt.executeQuery();

            while (rs.next()){
                String movieName = rs.getString("movieName");
                int yearOfRelease = rs.getInt("yearOfRelease");
                String genre = rs.getString("genre");
                double IMDB = rs.getDouble("IMDB");
                int likes = rs.getInt("likes");
                int downloads = rs.getInt("downloads");
                String description = rs.getString("description");
                String director = rs.getString("director");
                String cast = rs.getString("cast");
                String movieLink = rs.getString("movieLink");
                String trailerLink = rs.getString("trailerLink");
                String movieImageURL = rs.getString("ImageURL");

                BorderPane appendTemplate = recommendedMovie.movieTemplate(movieName, yearOfRelease, genre, IMDB, likes, downloads, description, director, cast, movieLink, trailerLink, movieImageURL);

                if (count++<2)
                    horizontalBoxFirst.getChildren().add(appendTemplate);
                else
                    horizontalBoxSecond.getChildren().add(appendTemplate);
            }

            verticalTemplates.getChildren().addAll(horizontalBoxFirst,horizontalBoxSecond);
            movieResults.setCenter(verticalTemplates);

        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            DBUtils.closeAll(rs,stmtt,conn);
        }
        return movieResults;
    }
}
