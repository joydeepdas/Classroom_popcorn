package com.ClassroomPopcorn.main.template;

import com.ClassroomPopcorn.database.logIn.userLoggedIn;
import com.ClassroomPopcorn.database.utils.DBUtils;
import com.ClassroomPopcorn.main.windows.login.userLogin;
import com.ClassroomPopcorn.main.windows.movie.movieOnline;
import com.ClassroomPopcorn.main.functions.*;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.sql.*;


public class searchMovie {

    public static BorderPane movieTemplate(String movieid, String movieName, int yearOfRelease, double IMDB, String description, String imageurl)
    {
        BorderPane movies = new BorderPane();
        movies.setPadding(new Insets(10,10,10,30));
        movies.setStyle("-fx-border-color: black");

        Image img = new Image(imageurl);
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(200);
        imgView.setPreserveRatio(true);
        movies.setLeft(imgView);

        VBox details = new VBox(10);

        Label name = new Label(movieName);
        name.setPadding(new Insets(0,0,20,20));
        name.setFont(new Font("Cambria", 25));
        name.setTextFill(Color.web("#fff"));

        Label year = new Label(yearOfRelease+"");
        year.setPadding(new Insets(0,0,0,20));
        year.setFont(new Font("Cambria", 20));
        year.setTextFill(Color.web("#fff"));

        Label imdb = new Label("IMDB: "+IMDB);
        imdb.setPadding(new Insets(0,0,0,20));
        imdb.setFont(new Font("Cambria", 20));
        imdb.setTextFill(Color.web("#fff"));

        details.getChildren().addAll(name,year,imdb);

        movies.setCenter(details);

        movies.setOnMouseClicked(e-> {
            e.consume();

            String[] status = userLoggedIn.userLoggedIn();

            movieOnline ob = new movieOnline();
            Connection con = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Timestamp ts = new Timestamp(System.currentTimeMillis());

            String msn = get_msn.getMotherboardSN();
            String dummy=null, label = "", query="";

            if (status[2]!=null)
            {
                query = "Select Email_Id from classroompopcorn.User_search where Email_Id ='"+status[2]+"' and MID='"+movieid+"';";
                label ="Email_Id";
            }
            else
            {
                query = "Select MSN from classroompopcorn.System_search where MSN ='"+msn+"' and MID='"+movieid+"';";
                label ="MSN";
            }
            try{
                con = DBUtils.getConnection();
                stmt = con.prepareStatement(query);
                rs = stmt.executeQuery();
                rs.beforeFirst();
                while(rs.next())
                {
                    dummy = rs.getString(label);
                }
            }
            catch(SQLException sql){
                sql.printStackTrace();
            }
            finally{
                DBUtils.closeStatement(stmt);
                DBUtils.closeConnection(con);
            }

            String query1 = "update classroompopcorn.System_search set time_stamp = '"+ts+"' where MSN='"+msn+"' and MID='"+movieid+"';";
            String query2 = DBUtils.prepareInsertQuery("classroompopcorn.System_search","MSN, MID, time_stamp","?,?,?");
            String query3 = "update classroompopcorn.User_search set time_stamp = '"+ts+"' where Email_Id='"+status[2]+"' and MID='"+movieid+"';";
            String query4 = DBUtils.prepareInsertQuery("classroompopcorn.User_search","Email_Id, MID, time_stamp","?,?,?");

            try{
                con = DBUtils.getConnection();
                if(status[2]!=null)
                {
                    if(dummy!=null) {
                        stmt = con.prepareStatement(query3);
                        stmt.executeUpdate();
                    }
                    else {
                        stmt = con.prepareStatement(query4);
                        stmt.setString(1, status[2]);
                        stmt.setString(2, movieid);
                        stmt.setTimestamp(3, ts);
                        stmt.executeUpdate();
                    }
                }
                else
                {
                    if(dummy!=null) {
                        stmt = con.prepareStatement(query1);
                        stmt.executeUpdate();
                    }
                    else {
                        stmt = con.prepareStatement(query2);
                        stmt.setString(1, msn);
                        stmt.setString(2, movieid);
                        stmt.setTimestamp(3, ts);
                        stmt.executeUpdate();
                    }
                }
            }
            catch(SQLException sql){
                sql.printStackTrace();
            }
            finally{
                DBUtils.closeStatement(stmt);
                DBUtils.closeConnection(con);
            }

            ob.movieOnline(movieid, movieName, yearOfRelease, IMDB, description);
        });

        movies.setCursor(Cursor.HAND);
        return movies;
    }
    public static BorderPane movieTemplate(String movieName, int yearOfRelease, String genre, double IMDB, int likes, int downloads, String description, String director, String cast, String movieLink, String trailerLink, String movieImageURL){
        BorderPane movies = new BorderPane();
        movies.setPadding(new Insets(10,10,10,30));
        movies.setStyle("-fx-border-color: black");

        Image img = new Image(movieImageURL);
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(200);
        imgView.setPreserveRatio(true);
        movies.setLeft(imgView);

        VBox details = new VBox(10);

        Label name = new Label(movieName);
        name.setPadding(new Insets(0,0,20,20));
        name.setFont(new Font("Cambria", 25));
        name.setTextFill(Color.web("#fff"));

        Label year = new Label(yearOfRelease+"");
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

        Label download = GlyphsDude.createIconLabel( FontAwesomeIcon.DOWNLOAD,
                "Downloads: "+downloads,
                "16",
                "18",
                ContentDisplay.LEFT );
        download.setPadding(new Insets(0,0,0,20));
        download.setFont(new Font("Cambria", 20));
        download.setTextFill(Color.web("#fff"));

        details.getChildren().addAll(name,year,genres,imdb,download);

        movies.setCenter(details);

        movies.setOnMouseClicked(e-> {
            e.consume();
            movieOnline ob = new movieOnline();
            ob.movieOnline(movieName, yearOfRelease, genre, IMDB, likes, downloads, description, director, cast, movieLink, trailerLink, movieImageURL);
        });

        movies.setCursor(Cursor.HAND);
        return movies;
    }
}
