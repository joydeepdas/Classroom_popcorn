package com.ClassroomPopcorn.main.windows.movie;

import com.ClassroomPopcorn.database.getMovies.movieDetails;
import com.ClassroomPopcorn.main.functions.*;
import com.ClassroomPopcorn.database.getMovies.recommendedMoviesDetail;

import com.ClassroomPopcorn.database.logIn.userLoggedIn;
import com.ClassroomPopcorn.database.utils.DBUtils;
import com.ClassroomPopcorn.main.windows.home.main;
import com.ClassroomPopcorn.main.windows.login.userLogin;
import com.ClassroomPopcorn.main.windows.movie.ClassNameHere;
import com.ClassroomPopcorn.main.windows.movie.ImagePopup;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.naming.*;
import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public class movieOnline {

    String movieurl="";
    public void movieOnline(String movieid, String movieName, int yearOfRelease, double IMDB, String description)
    {
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);

        Connection conn = null, conn2 = null;
        PreparedStatement stmtt = null, stmtt2 = null, stmtt3 = null, stmtt4=null, stmtt5=null;
        ResultSet rs = null, rs2 = null, rs3= null, rs4 = null, rs5= null;

        int runtime=0,video_qual=0;
        String trailerurl="",genre="", directors="", actors="", producers="", dir_url="" , cast_url="", prod_url="";

        String query = "select * from classroompopcorn.Stream_Info where MID = '"+movieid+"';";
        String query2 = "select * from classroompopcorn.Movie_Genres where MID = '"+movieid+"';";
        String query3 = "select * from classroompopcorn.Person natural join classroompopcorn.Credit where MID = '"+movieid+"' and Role = 'Director';";
        String query4 = "select * from classroompopcorn.Person natural join classroompopcorn.Credit where MID = '"+movieid+"' and Role = 'Actor';";
        String query5 = "select * from classroompopcorn.Person natural join classroompopcorn.Credit where MID = '"+movieid+"' and Role = 'Producer';";

        try {
            conn = DBUtils.getConnection();
            stmtt = conn.prepareStatement(query);
            rs = stmtt.executeQuery();
            stmtt2 = conn.prepareStatement(query2);
            rs2 = stmtt2.executeQuery();
            stmtt3 = conn.prepareStatement(query3);
            rs3 = stmtt3.executeQuery();
            stmtt4 = conn.prepareStatement(query4);
            rs4 = stmtt4.executeQuery();
            stmtt5 = conn.prepareStatement(query5);
            rs5 = stmtt5.executeQuery();
            rs.beforeFirst();
            rs2.beforeFirst();
            rs3.beforeFirst();
            rs4.beforeFirst();
            rs5.beforeFirst();

            while (rs.next()) {
                runtime = rs.getInt("Running_time");
                video_qual=rs.getInt("Video_quality");
                movieurl = rs.getString("Movie_url");
                trailerurl = rs.getString("Trailer_url");
            }
            while (rs2.next()) {
                String dummy = rs2.getString("Genres");
                genre = genre + dummy+", ";
            }
            while(rs3.next()){
                String dummy = rs3.getString("P_Name");
                directors = directors + dummy+", ";
                dir_url = rs3.getString("Image_url");
            }
            while(rs4.next()){
                String dummy = rs4.getString("P_Name");
                actors = actors + dummy+", ";
                cast_url = rs4.getString("Image_url");
            }
            while(rs5.next()){
                String dummy = rs5.getString("P_Name");
                producers = producers + dummy+", ";
                prod_url = rs5.getString("Image_url");
            }
            if(genre.length()>0)
                genre = genre.substring(0,genre.length()-2);
            if(directors.length()>0)
                directors = directors.substring(0,directors.length()-2);
            if(actors.length()>0)
                actors = actors.substring(0,actors.length()-2);
            if(producers.length()>0)
                producers = producers.substring(0,producers.length()-2);
        }
        catch (SQLException sql) {
            sql.printStackTrace();
        }

        Stage movieWindow = new Stage();
        movieWindow.setTitle("Enjoy Live Streaming!!");

        ScrollPane details = new ScrollPane();
        details.setFitToWidth(true);

        WebView webview = new WebView();
        webview.getEngine().load(movieurl);

        WebView trailerview = new WebView();
        trailerview.getEngine().load( trailerurl );
        trailerview.setPrefSize(512,288);

        VBox verticalStack = new VBox(20);
        verticalStack.setStyle("-fx-background-color: #323232;");
        verticalStack.setFocusTraversable(true);

        BorderPane mainMovie = new BorderPane(webview);

        BorderPane movieDetails = new BorderPane();
        movieDetails.setPadding(new Insets(0,0,50,0));
        movieDetails.setLeft(new BorderPane(trailerview));

        VBox info = new VBox(10);
        info.setAlignment(Pos.TOP_LEFT);

        Label name = new Label(movieName);
        name.setPadding(new Insets(0,0,0,20));
        name.setFont(new Font("Cambria", 30));
        name.setTextFill(Color.web("#fff"));

        Label video_qu = new Label(runtime+" min - "+video_qual+"p");
        video_qu.setPadding(new Insets(0,0,15,20));
        video_qu.setFont(new Font("Cambria", 20));
        video_qu.setTextFill(Color.web("#fff"));

        Label year = new Label("Year of Release: "+yearOfRelease);
        year.setPadding(new Insets(0,0,0,20));
        year.setFont(new Font("Cambria", 20));
        year.setTextFill(Color.web("#fff"));

        Label genres = new Label(genre);
        genres.setPadding(new Insets(-5,0,0,20));
        genres.setFont(new Font("Cambria", 20));
        genres.setTextFill(Color.web("#fff"));

        Label imdb = new Label("IMDB: "+IMDB);
        imdb.setPadding(new Insets(0,0,0,20));
        imdb.setFont(new Font("Cambria", 20));
        imdb.setTextFill(Color.web("#fff"));

        Label synopsis = new Label(description);
        synopsis.setPadding(new Insets(5,0,0,20));
        synopsis.setFont(new Font("Cambria", 18));
        synopsis.setTextFill(Color.web("#fff"));

        final String dir_url1 = dir_url;
        final String cast_url1 = cast_url;
        final String prod_url1 = prod_url;

        Label cast = new Label("Cast: "+actors);
        cast.setPadding(new Insets(10,0,0,20));
        cast.setFont(new Font("Cambria", 20));
        cast.setTextFill(Color.web("#fff"));
        cast.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                ImagePopup obj = new ImagePopup();
                obj.imagePopupWindowShow(cast_url1);
            }
        });
        //cast.setVisible(false);

        Label director = new Label("Directors: "+directors);
        director.setPadding(new Insets(0,0,0,20));
        director.setFont(new Font("Cambria", 20));
        director.setTextFill(Color.web("#fff"));
        director.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                ImagePopup obj = new ImagePopup();
                obj.imagePopupWindowShow(dir_url1);
            }
        });


        Label producer = new Label("Producers: "+producers);
        producer.setPadding(new Insets(0,0,0,20));
        producer.setFont(new Font("Cambria", 20));
        producer.setTextFill(Color.web("#fff"));
        producer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                ImagePopup obj = new ImagePopup();
                obj.imagePopupWindowShow(prod_url1);
            }
        });

        HBox searchRow4 = new HBox(5);
        searchRow4.setAlignment(Pos.TOP_LEFT);

        VBox countryCollection = new VBox(0);
        ComboBox countryComboBox = new ComboBox();
        countryComboBox.getItems().addAll(
                "All",
                "India",
                "Sri Lanka",
                "Australia",
                "New Zealand",
                "Pakistan",
                "West Indies",
                "Nepal",
                "Maldives",
                "Bangladesh",
                "Zimbabwe",
                "Bhutan",
                "France",
                "Germany",
                "USA"
        );
        countryComboBox.getSelectionModel().selectFirst();
        countryCollection.getChildren().addAll(countryComboBox);

        Button searchButton4 = new Button("Select Country");
        searchButton4.setStyle("-fx-focus-color: transparent;");
        searchButton4.setFont(new Font("Cambria", 18));
        searchButton4.setStyle("-fx-background-color: #6ac045;");
        searchButton4.setTextFill(Color.web("#fff"));
        searchButton4.setCursor(Cursor.HAND);

        searchRow4.setPadding(new Insets(8,0,0,20));
        searchRow4.getChildren().addAll(countryCollection,searchButton4);

        HBox searchRow3 = new HBox(5);
        searchRow3.setAlignment(Pos.TOP_LEFT);

        Button searchButton2 = new Button("Bookmark");
        searchButton2.setStyle("-fx-focus-color: transparent;");
        searchButton2.setFont(new Font("Cambria", 18));
        searchButton2.setStyle("-fx-background-color: #6ac045;");
        searchButton2.setTextFill(Color.web("#fff"));
        searchButton2.setCursor(Cursor.HAND);

        searchRow3.setPadding(new Insets(0,0,0,20));
        searchRow3.getChildren().addAll(searchButton2);

        main.scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.ENTER),
                () -> searchButton2.fire()
        );

        HBox searchRow1 = new HBox(5);
        searchRow1.setAlignment(Pos.TOP_LEFT);

        VBox RatingsCollection = new VBox(0);
        ComboBox rateComboBox = new ComboBox();
        rateComboBox.getItems().addAll(
                "10",
                "9",
                "8",
                "7",
                "6",
                "5",
                "4",
                "3",
                "2",
                "1"
        );
        rateComboBox.getSelectionModel().selectFirst();
        RatingsCollection.getChildren().addAll(rateComboBox);
        RatingsCollection.setPadding(new Insets(0,0,0,0));

        Button searchButton = new Button("Rate");
        searchButton.setStyle("-fx-focus-color: transparent;");
        searchButton.setFont(new Font("Cambria", 18));
        searchButton.setStyle("-fx-background-color: #6ac045;");
        searchButton.setTextFill(Color.web("#fff"));
        searchButton.setCursor(Cursor.HAND);

        main.scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.ENTER),
                () -> searchButton.fire()
        );

        searchRow1.setPadding(new Insets(0,0,0,20));
        searchRow1.getChildren().addAll(RatingsCollection,searchButton);

        HBox searchRow = new HBox(5);
        searchRow.setAlignment(Pos.TOP_LEFT);

        TextField searchBox = new TextField();
        searchBox.setPromptText("Comment here");
        searchBox.setStyle("-fx-focus-color: transparent;");
        searchBox.setPrefColumnCount(35);
        searchBox.setPrefHeight(35);
        searchBox.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                movieDetails.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

        Button searchButton1 = new Button("Comment");
        searchButton1.setStyle("-fx-focus-color: transparent;");
        searchButton1.setFont(new Font("Cambria", 18));
        searchButton1.setStyle("-fx-background-color: #6ac045;");
        searchButton1.setTextFill(Color.web("#fff"));
        searchButton1.setCursor(Cursor.HAND);

        main.scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.ENTER),
                () -> searchButton.fire()
        );

        searchRow.setPadding(new Insets(0,0,0,20));
        searchRow.getChildren().addAll(searchBox,searchButton1);

        info.getChildren().addAll(name, video_qu, year, imdb, genres, synopsis, cast, director, producer, searchRow4, searchRow3,searchRow1,searchRow);

        movieDetails.setCenter(info);

        HBox hbBottom = new HBox(50);
        hbBottom.setPadding(new Insets(30,0,0,0));

        verticalStack.getChildren().addAll(mainMovie, movieDetails);
        details.setContent(verticalStack);

        Scene scene = new Scene(details);
        scene.getStylesheets().add(movieOnline.class.getResource("../../resources/css/main.css").toExternalForm());
        movieWindow.setScene(scene);

        movieWindow.setMaximized(true);
        movieWindow.show();

        movieWindow.widthProperty().addListener(e-> webview.setPrefWidth(movieWindow.getWidth()));

        movieWindow.setMinWidth(800);
        movieWindow.setMinHeight(600);
        webview.setPrefHeight(0.9*movieWindow.getHeight());

        movieWindow.getIcons().add(new Image(getClass().getResourceAsStream("../../resources/images/ClassroomPopcorn.png")));

        movieWindow.setOnCloseRequest(e-> {
            webview.getEngine().load(null);
            trailerview.getEngine().load(null);
        });

        searchButton.setOnAction(e->{

            String[] status = userLoggedIn.userLoggedIn();
            Connection conn6 = null;
            PreparedStatement stmtt6 = null;
            ResultSet rs6=null;
            if (status[2]!= null)
            {
                String rating2 = rateComboBox.getValue().toString();
                float rating = Float.valueOf(rating2);
                String query6 = "Select Email_ID from classroompopcorn.reviews where Email_ID = '"+status[2]+"' and MID ='"+movieid+"';";
                String dummy6=null;
                try {
                    conn6 = DBUtils.getConnection();
                    stmtt6 = conn6.prepareStatement(query6);
                    rs6 = stmtt6.executeQuery();
                    rs6.beforeFirst();
                    while (rs6.next()) {
                        dummy6 = rs6.getString("Email_Id");
                    }
                }
                catch (SQLException sql) {
                    sql.printStackTrace();
                }
                String query7="";
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                if(dummy6 != null)
                {
                    query7 = "Update classroompopcorn.reviews set Time_stamp ='"+ts+"' , Rating ='"+rating+"' where Email_Id = '"+status[2]+"' and MID = '"+movieid+"';";
                }
                else{
                    query7 = DBUtils.prepareInsertQuery("classroompopcorn.reviews","Email_Id, MID, Time_stamp, Rating, Comment","?,?,?,?,?");
                }
                try {
                    if(dummy6 != null){
                        conn6 = DBUtils.getConnection();
                        stmtt6 = conn6.prepareStatement(query7);
                        stmtt6.executeUpdate();
                    }
                    else{
                        conn6 = DBUtils.getConnection();
                        stmtt6 = conn6.prepareStatement(query7);
                        stmtt6.setString(1, status[2]);
                        stmtt6.setString(2, movieid);
                        stmtt6.setTimestamp(3, ts);
                        stmtt6.setFloat(4, rating);
                        stmtt6.setString(5, "");
                        stmtt6.executeUpdate();
                    }

                }
                catch (SQLException sql) {
                    sql.printStackTrace();
                }
                ClassNameHere.infoBox("Successfully Rated!", "CHEERS");
            }
            else{
                ClassNameHere.infoBox("Please Login First!", "WARNING");
            }
//
        });
        searchButton1.setOnAction(e->{

            String[] status = userLoggedIn.userLoggedIn();
            Connection conn6 = null;
            PreparedStatement stmtt6 = null;
            ResultSet rs6=null;
            String comment = searchBox.getText();
            if(comment.equals(""))
            {
                ClassNameHere.infoBox("Comment field can't be empty", "CHEERS");
            }
            else {

                if (status[2] != null) {
                    String query6 = "Select Email_ID from classroompopcorn.reviews where Email_ID = '" + status[2] + "' and MID ='" + movieid + "';";
                    String dummy6 = null;
                    try {
                        conn6 = DBUtils.getConnection();
                        stmtt6 = conn6.prepareStatement(query6);
                        rs6 = stmtt6.executeQuery();
                        rs6.beforeFirst();
                        while (rs6.next()) {
                            dummy6 = rs6.getString("Email_Id");
                        }
                    } catch (SQLException sql) {
                        sql.printStackTrace();
                    }
                    String query7 = "";
                    Timestamp ts = new Timestamp(System.currentTimeMillis());
                    if (dummy6 != null) {
                        query7 = "Update classroompopcorn.reviews set Time_stamp ='" + ts + "' , Comment ='" + comment + "' where Email_Id = '" + status[2] + "' and MID = '" + movieid + "';";
                    } else {
                        query7 = DBUtils.prepareInsertQuery("classroompopcorn.reviews", "Email_Id, MID, Time_stamp, Rating, Comment", "?,?,?,?,?");
                    }
                    try {
                        if (dummy6 != null) {
                            conn6 = DBUtils.getConnection();
                            stmtt6 = conn6.prepareStatement(query7);
                            stmtt6.executeUpdate();
                        } else {
                            conn6 = DBUtils.getConnection();
                            stmtt6 = conn6.prepareStatement(query7);
                            stmtt6.setString(1, status[2]);
                            stmtt6.setString(2, movieid);
                            stmtt6.setTimestamp(3, ts);
                            stmtt6.setFloat(4, 0);
                            stmtt6.setString(5, comment);
                            stmtt6.executeUpdate();
                        }
                    } catch (SQLException sql) {
                        sql.printStackTrace();
                    }
                    ClassNameHere.infoBox("Successfully Commented!", "CHEERS");
                } else {
                    ClassNameHere.infoBox("Please Login First!", "WARNING");
                }
                searchBox.setText("");
            }
        });
        searchButton2.setOnAction(e->{

            String[] status = userLoggedIn.userLoggedIn();
            Connection conn6 = null;
            PreparedStatement stmtt6 = null;
            ResultSet rs6=null;
            if (status[2]!= null) {
                String query6 = "Select Email_ID from classroompopcorn.bookmarks where Email_ID = '" + status[2] + "' and MID ='" + movieid + "';";
                String dummy6 = null;
                try {
                    conn6 = DBUtils.getConnection();
                    stmtt6 = conn6.prepareStatement(query6);
                    rs6 = stmtt6.executeQuery();
                    rs6.beforeFirst();
                    while (rs6.next()) {
                        dummy6 = rs6.getString("Email_Id");
                    }
                } catch (SQLException sql) {
                    sql.printStackTrace();
                }
                String query7 = "";
                if (dummy6==null) {
                    query7 = DBUtils.prepareInsertQuery("classroompopcorn.bookmarks", "Email_Id, MID, time_elapsed", "?,?,?");

                    try {
                        conn6 = DBUtils.getConnection();
                        stmtt6 = conn6.prepareStatement(query7);
                        stmtt6.setString(1, status[2]);
                        stmtt6.setString(2, movieid);
                        stmtt6.setInt(3, 0);
                        stmtt6.executeUpdate();
                        ClassNameHere.infoBox("Successfully Bookmarked!", "CHEERS");
                    } catch (SQLException sql) {
                        sql.printStackTrace();
                    }
                }
                else{
                    ClassNameHere.infoBox("Successfully Bookmarked!", "CHEERS");
                }
                }
            else {
                ClassNameHere.infoBox("Please Login First!", "WARNING");
            }
//
        });
        searchButton4.setOnAction(e-> {
            String c_select = countryComboBox.getValue().toString(),dummy7="",dummy8="",dummy9="";
            String quer = "select Certificate from classroompopcorn.Movie_Certificates where MID ='"+movieid+"' and C_Name='"+c_select+"';";
            String quer2 = "select Release_Date from classroompopcorn.Movie_dates where MID ='"+movieid+"' and C_Name='"+c_select+"';";
            String quer3 = "select Languages from classroompopcorn.Movie_Languages where MID ='"+movieid+"' and C_Name='"+c_select+"';";

            Connection conn7 = null,conn8 = null,conn9 = null;
            PreparedStatement stmtt7 = null,stmtt8 = null,stmtt9 = null;
            ResultSet rs7=null,rs8=null,rs9=null;

            try {
                conn7 = DBUtils.getConnection();
                stmtt7 = conn7.prepareStatement(quer);
                rs7 = stmtt7.executeQuery();
                conn8 = DBUtils.getConnection();
                stmtt8 = conn8.prepareStatement(quer2);
                rs8 = stmtt8.executeQuery();
                conn9 = DBUtils.getConnection();
                stmtt9 = conn9.prepareStatement(quer3);
                rs9 = stmtt9.executeQuery();
                rs7.beforeFirst();
                rs8.beforeFirst();
                rs9.beforeFirst();
                while (rs7.next()) {
                    dummy7 = rs7.getString("Certificate");
                }
                while(rs8.next())
                {
                    dummy8 = dummy8+rs8.getString("Release_Date")+", ";
                }
                while(rs9.next())
                {
                    dummy9 = dummy9+rs9.getString("Languages")+", ";
                }
            } catch (SQLException sql) {
                sql.printStackTrace();
            }
            if(dummy8.length()>0)
                dummy8 = dummy8.substring(0,dummy8.length()-2);
            if(dummy9.length()>0)
                dummy9 = dummy9.substring(0,dummy9.length()-2);
            if(dummy7.length()==0&&dummy8.length()==0&&dummy9.length()==0)
                ClassNameHere.infoBox("Information Not Available",c_select);
            else
                ClassNameHere.infoBox("Certificate: "+dummy7+"\nRelease_Dates: "+dummy8+"\nLanguages: "+dummy9,c_select);
        });

    }
    public void movieOnline(String movieName, int yearOfRelease, String genre, double IMDB, int likes, int downloads, String description, String director, String cast, String movieLink, String trailerLink, String movieImageURL){
        Stage movieWindow = new Stage();
        movieWindow.setTitle("Enjoy Live Streaming !!");

        ScrollPane details = new ScrollPane();
        details.setFitToWidth(true);

        WebView webview = new WebView();
        webview.getEngine().load( movieLink );

        WebView trailerview = new WebView();
        trailerview.getEngine().load( trailerLink );
        trailerview.setPrefSize(512,288);

        VBox verticalStack = new VBox(20);
        verticalStack.setStyle("-fx-background-color: #323232;");
        verticalStack.setFocusTraversable(true);

        BorderPane mainMovie = new BorderPane(webview);

        BorderPane movieDetails = new BorderPane();
        movieDetails.setPadding(new Insets(0,0,50,0));
        movieDetails.setLeft(new BorderPane(trailerview));

        VBox info = new VBox(10);
        info.setAlignment(Pos.TOP_LEFT);

        Label name = new Label(movieName);
        name.setPadding(new Insets(0,0,20,20));
        name.setFont(new Font("Cambria", 25));
        name.setTextFill(Color.web("#fff"));

        Label year = new Label("Year of Release:"+yearOfRelease);
        year.setPadding(new Insets(0,0,0,20));
        year.setFont(new Font("Cambria", 20));
        year.setTextFill(Color.web("#fff"));

        Label genres = new Label(genre);
        genres.setPadding(new Insets(-5,0,0,20));
        genres.setFont(new Font("Cambria", 20));
        genres.setTextFill(Color.web("#fff"));

        Label imdb = new Label("IMDB: "+IMDB);
        imdb.setPadding(new Insets(0,0,0,20));
        imdb.setFont(new Font("Cambria", 20));
        imdb.setTextFill(Color.web("#fff"));

        Label like = GlyphsDude.createIconLabel( FontAwesomeIcon.HEART,
                "Likes: "+ likes,
                "16",
                "18",
                ContentDisplay.LEFT );
        like.setPadding(new Insets(0,0,0,20));
        like.setFont(new Font("Cambria", 20));
        like.setTextFill(Color.web("#fff"));

        Label download = GlyphsDude.createIconLabel( FontAwesomeIcon.DOWNLOAD,
                "Downloads: "+downloads,
                "16",
                "18",
                ContentDisplay.LEFT );
        download.setPadding(new Insets(0,0,0,20));
        download.setFont(new Font("Cambria", 20));
        download.setTextFill(Color.web("#fff"));

        info.getChildren().addAll(name, year, genres, imdb, like, download);

        movieDetails.setCenter(info);

        BorderPane recommendation = recommendedMoviesDetail.movies("1");
        movieWindow.widthProperty().addListener(e-> {
            if (movieWindow.getWidth()<1100)
                movieDetails.setRight(new Label());
            else
                movieDetails.setRight(recommendation);
        });
        movieDetails.setRight(recommendation);

        HBox hbBottom = new HBox(50);
        hbBottom.setPadding(new Insets(30,0,0,0));

        Label synopsis = new Label("Synopsis: \n\n "+description);
        synopsis.setPadding(new Insets(0,0,0,20));
        synopsis.setFont(new Font("Cambria", 16));
        synopsis.setTextFill(Color.web("#fff"));
        synopsis.setWrapText(true);
        synopsis.widthProperty().addListener(e->synopsis.setPrefWidth(0.6*movieWindow.getWidth()));

        VBox vb = new VBox(10);

        Label directorLabel = new Label("Director");
        directorLabel.setPadding(new Insets(0,0,0,20));
        directorLabel.setFont(new Font("Cambria", 18));
        directorLabel.setTextFill(Color.web("#a1a1a1"));
        directorLabel.setStyle("-fx-border-color: black;-fx-border-width: 0 0 1 0;-fx-underline: true;-fx-padding: 0 0 -1 0;");

        Label directorName = GlyphsDude.createIconLabel( FontAwesomeIcon.USER,
                director,
                "18",
                "18",
                ContentDisplay.LEFT );
        directorName.setPadding(new Insets(0,0,10,20));
        directorName.setFont(new Font("Cambria", 18));
        directorName.setTextFill(Color.web("#a1a1a1"));

        Label castLabel = new Label("Casts");
        castLabel.setPadding(new Insets(0,0,0,20));
        castLabel.setFont(new Font("Cambria", 18));
        castLabel.setTextFill(Color.web("#a1a1a1"));
        castLabel.setStyle("-fx-border-color: black;-fx-border-width: 0 0 1 0;-fx-underline: true;-fx-padding: 0 0 -1 0;");

        vb.getChildren().addAll(directorLabel,directorName,castLabel);

        String[] casts = cast.split(" / ");
        for (int i=0; i<casts.length;++i) {
            String castInloop = casts[i];
            Label castName = GlyphsDude.createIconLabel( FontAwesomeIcon.USER,
                    castInloop+"",
                    "18",
                    "18",
                    ContentDisplay.LEFT );
            castName.setPadding(new Insets(0,0,0,20));
            castName.setFont(new Font("Cambria", 18));
            castName.setTextFill(Color.web("#a1a1a1"));
            vb.getChildren().addAll(castName);
        }

        hbBottom.getChildren().addAll(synopsis,vb);
        movieDetails.setBottom(hbBottom);

        verticalStack.getChildren().addAll(mainMovie, movieDetails);
        details.setContent(verticalStack);

        Scene scene = new Scene(details);
        scene.getStylesheets().add(movieOnline.class.getResource("../../resources/css/main.css").toExternalForm());
        movieWindow.setScene(scene);

        movieWindow.setMaximized(true);
        movieWindow.show();

        movieWindow.widthProperty().addListener(e-> webview.setPrefWidth(movieWindow.getWidth()));

        movieWindow.setMinWidth(800);
        movieWindow.setMinHeight(600);
        webview.setPrefHeight(0.9*movieWindow.getHeight());

        movieWindow.getIcons().add(new Image(getClass().getResourceAsStream("../../resources/images/ClassroomPopcorn.png")));

        movieWindow.setOnCloseRequest(e-> {
            webview.getEngine().load(null);
            trailerview.getEngine().load(null);
        });
    }


}