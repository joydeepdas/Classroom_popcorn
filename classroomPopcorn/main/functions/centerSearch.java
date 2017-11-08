package com.ClassroomPopcorn.main.functions;

import com.ClassroomPopcorn.database.getMovies.movieDetails;
import com.ClassroomPopcorn.database.logIn.userLoggedIn;
import com.ClassroomPopcorn.main.windows.home.main;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;


import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.image.BufferedImage;
import java.io.File;

public class centerSearch {

    public static BorderPane centerSearch(){
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        BorderPane searchLayout = new BorderPane();
        searchLayout.setPadding(new Insets(70,0,0,0));
        searchLayout.setStyle("-fx-background-color: #171717;");

        //=================================================================================
        VBox searchVB = new VBox(15);
        searchVB.setAlignment(Pos.TOP_CENTER);

        Label searchLabel = new Label("Search Term: ");
        searchLabel.setTextFill(Color.web("#fff"));
        searchLabel.setFont(new Font("Cambria", 25));
        searchLabel.setTextFill(Color.web("#5a5a5a"));

        //=================================================================================
        HBox searchRow = new HBox(25);
        searchRow.setAlignment(Pos.TOP_CENTER);

        TextField searchBox = new TextField();
        searchBox.setPromptText("By movie name, keywords, etc.");
        searchBox.setStyle("-fx-focus-color: transparent;");
        searchBox.setPrefColumnCount(35);
        searchBox.setPrefHeight(35);
        searchBox.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                searchLayout.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-focus-color: transparent;");
        searchButton.setFont(new Font("Cambria", 18));
        searchButton.setStyle("-fx-background-color: #6ac045;");
        searchButton.setTextFill(Color.web("#fff"));
        searchButton.setCursor(Cursor.HAND);

        main.scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.ENTER),
                () -> searchButton.fire()
        );

        searchRow.getChildren().addAll(searchBox,searchButton);

        //=================================================================================
        HBox filterRow = new HBox(25);
        filterRow.setAlignment(Pos.TOP_CENTER);

        VBox genreCollection = new VBox(10);
        Label genreLabel = new Label("Genre: ");
        genreLabel.setFont(new Font("Cambria", 20));
        genreLabel.setTextFill(Color.web("#5a5a5a"));
        ComboBox genreComboBox = new ComboBox();
        genreComboBox.getItems().addAll(
                "All",
                "Action",
                "Adventure",
                "Animation",
                "Comedy",
                "Crime",
                "Fiction",
                "Historical",
                "Horror",
                "Mystery",
                "Romantic",
                "Thriller"
        );
        genreComboBox.getSelectionModel().selectFirst();
        genreCollection.getChildren().addAll(genreLabel,genreComboBox);

        VBox ratingCollection = new VBox(10);
        Label ratingLabel = new Label("Rating: ");
        ratingLabel.setFont(new Font("Cambria", 20));
        ratingLabel.setTextFill(Color.web("#5a5a5a"));
        ComboBox ratingComboBox = new ComboBox();
        ratingComboBox.getItems().addAll(
                "All",
                "9+",
                "8+",
                "7+",
                "6+",
                "5+",
                "4+",
                "3+",
                "2+",
                "1+"
        );
        ratingComboBox.getSelectionModel().selectFirst();
        ratingCollection.getChildren().addAll(ratingLabel,ratingComboBox);

        VBox orderCollection = new VBox(10);
        Label orderLabel = new Label("Order By: ");
        orderLabel.setFont(new Font("Cambria", 20));
        orderLabel.setTextFill(Color.web("#5a5a5a"));
        ComboBox orderComboBox = new ComboBox();
        orderComboBox.getItems().addAll(
                "Latest",
                "Recommended",
                "Trending",
                "Oldest",
                "User_Rating",
                "Alphabetical"
        );
        orderComboBox.getSelectionModel().selectFirst();
        orderCollection.getChildren().addAll(orderLabel,orderComboBox);

        VBox dummybox = new VBox(10);

        Label dummy = new Label(" ");
        dummy.setFont(new Font("Cambria", 20));

        Button bookmarkButton = new Button("My Bookmarks");
        bookmarkButton.setStyle("-fx-focus-color: transparent;");
        bookmarkButton.setFont(new Font("Cambria", 16));
        bookmarkButton.setStyle("-fx-background-color: #6ac045;");
        bookmarkButton.setTextFill(Color.web("#fff"));
        bookmarkButton.setCursor(Cursor.HAND);

        dummybox.getChildren().addAll(dummy,bookmarkButton);

        filterRow.getChildren().addAll(genreCollection,ratingCollection,orderCollection);

        //=================================================================================
        searchVB.getChildren().addAll(searchLabel, searchRow, filterRow, bookmarkButton);
        searchVB.setPadding(new Insets(0,0,50,0));

        final BorderPane searchResult = new BorderPane(movieDetails.movieDetails("ORDER BY Release_Year desc"," ",0));

        bookmarkButton.setOnAction(event -> {
            String [] status = userLoggedIn.userLoggedIn();
            String login_details;

            int flag_login=0;

            if (status[2]==null){
                login_details = get_msn.getMotherboardSN();

            }
            else{
                login_details  = status[2];
                flag_login = 1;
            }
            searchResult.getChildren().clear();
            searchResult.setCenter(movieDetails.movieDetails("bookmark",login_details,flag_login));

            searchBox.requestFocus(); // Delegate the focus to container
        });

        searchButton.setOnAction(e->{

            String condition="";
            String genre = genreComboBox.getValue().toString();
            genre = genre.equals("All") ? "" : genre;
            String [] status = userLoggedIn.userLoggedIn();
            String login_details;

            int flag_login=0;

            if (status[2]==null){
                login_details = get_msn.getMotherboardSN();

            }
            else{
                login_details  = status[2];
                flag_login = 1;
            }
            String rate = ratingComboBox.getValue().toString();
            rate = rate.charAt(0)=='A' ? "1" :  rate.charAt(0)+"";

            if (genre.equals(""))
                condition="WHERE Imdb_rating>"+rate;
            else
                condition="WHERE Imdb_rating>"+rate+" AND Genres LIKE '%"+genre+"%'";

            if (!searchBox.getText().isEmpty())
                condition = condition+" AND Title LIKE '%"+searchBox.getText()+"%' OR Keywords LIKE '%"+searchBox.getText()+"%'";

            String orderBy = orderComboBox.getValue().toString();
            if (orderBy.equals("Latest"))
                condition = condition + " ORDER BY Release_Year desc";
            else if (orderBy.equals("Oldest"))
                condition = condition + " ORDER BY Release_Year asc";
            else if (orderBy.equals("Alphabetical"))
                condition = condition + " ORDER BY Title asc";
            else if (orderBy.equals("Trending"))
                condition = condition + "Z";
            else if (orderBy.equals("Recommended"))
                condition = condition + "Y";
            else if (orderBy.equals("User_Rating"))
                condition = condition + "X";

            searchResult.getChildren().clear();
            searchResult.setCenter(movieDetails.movieDetails(condition,login_details,flag_login));

            searchBox.requestFocus(); // Delegate the focus to container
        });
        searchResult.setStyle("-fx-background-color: #1d1d1d;");
        searchResult.setPadding(new Insets(0,100,20,100));

        searchLayout.setTop(searchVB);
        searchLayout.setBottom(searchResult);

        return searchLayout;
    }
}
