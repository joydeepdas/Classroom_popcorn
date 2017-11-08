package com.ClassroomPopcorn.database.getMovies;

import com.ClassroomPopcorn.database.utils.DBUtils;
import com.ClassroomPopcorn.main.template.searchMovie;

import com.ClassroomPopcorn.main.windows.movie.ClassNameHere;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class movieDetails {

    public static StackPane movieDetails(String condition,String login_details, int flag_login){

        String query="";
        StackPane movieResults = new StackPane();
        VBox verticalTemplates = new VBox(10);
        verticalTemplates.setPadding(new Insets(30,0,0,0));

        Connection conn = null;
        PreparedStatement stmtt = null;
        ResultSet rs = null;

        int flag = 0 ;
        if(condition.equals("bookmark")){
            if(flag_login==1) {
                query = "select MID,Title, Synopsis,Release_Year, Imdb_rating, Image_url from movie natural join stream_info where MID in (select distinct MID from bookmarks where Email_Id= '" + login_details + "');";
                //else give a popup here and end it

                flag = 3;
            }
            else{
//                String titleBar = "Alert!!!";
//                String headerMessage = "Login Alert!!!";
//                String infoMessage = "Please Signup/Login";
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle(titleBar);
//                alert.setHeaderText(headerMessage);
//                alert.setContentText(infoMessage);
//                alert.showAndWait();
                ClassNameHere.infoBox("Please Signup/Login","Alert!!!","Login Alert!!!");
                flag=0;
                condition="Z";
            }

        }
        if((condition.substring(condition.length() - 1,condition.length())).equals("Z")) {

            flag=1;
        }
        if((condition.substring(condition.length() - 1,condition.length())).equals("Y")) {

            flag=2;
        }
        if((condition.substring(condition.length() - 1,condition.length())).equals("X")) {

            flag=4;
        }
        //System.out.println("here: "+(condition.substring(condition.length() - 1,condition.length())));
        if( flag==1) {
            query = "select distinct A.MID as MID, Title, Release_Year, Imdb_rating, Synopsis, Image_url \n" +
                    "from ((((select distinct MID ,sum((time_stamp)) as ST1\n" +
                    "\t  from user_search as U\n" +
                    "\t  group by U.MID) as A join\n" +
                    "\t  (select distinct MID ,sum((time_stamp)) as ST2\n" +
                    "\t  from system_search as S\n" +
                    "\t  group by S.MID) as B on (A.MID=B.MID)) join movie  on (A.MID=movie.MID) ) join stream_info on (A.MID=stream_info.MID)) \n" +
                    "\t  \n" +
                    "order by A.ST1+B.ST2 desc limit 5;\n";
        }
        else if( flag==4)  query = " select distinct A.MID, title, release_year, imdb_rating, Synopsis, image_url\n" +
                "from (select distinct R.MID, sum(rating) as SR1\n" +
                "      from reviews as R\n" +
                "      group by R.MID) as A natural join movie natural join stream_info\n" +
                "order by A.SR1 desc limit 5;";
        else if( flag==2 && flag_login==1) query = " select distinct A.MID, Title, Release_Year, Imdb_rating, Synopsis, Image_url \n" +
                "from (((select distinct M01.MID,count(distinct U.MID) as CM1\n" +
                "\t  from user_search as U, movie_genres as G1,movie_genres as G2,movie as M01\n" +
                "          where U.email_id= '"+login_details+"' and U.MID= G1.MID and G1.genres = G2.genres and \n" +
                "          G2.MID = M01.MID\n" +
                "\t  group by M01.MID) as A join movie  on (A.MID=movie.MID) ) join stream_info on (A.MID=stream_info.MID)) \n" +
                "order by A.CM1 desc limit 5; " ;


        else if( flag==2 && flag_login==0) query = " select distinct A.MID, Title, Release_Year, Imdb_rating, Synopsis, Image_url \n" +
                "from (((select distinct M01.MID,count(distinct S.MID) as CM1\n" +
                "\t  from system_search as S, movie_genres as G1,movie_genres as G2,movie as M01\n" +
                "          where S.MSN='"+login_details+"' and S.MID= G1.MID and G1.genres = G2.genres and \n" +
                "          G2.MID = M01.MID\n" +
                "\t  group by M01.MID) as A join movie  on (A.MID=movie.MID) ) join stream_info on (A.MID=stream_info.MID))   \n" +
                "order by A.CM1 desc limit 5;";

        if(flag==0) query = DBUtils.prepareSelectQuery("", "classroompopcorn.Movie","classroompopcorn.Stream_Info","classroompopcorn.movie_genres","classroompopcorn.movie_keywords","", condition+"");

        try {
            conn = DBUtils.getConnection();
            stmtt = conn.prepareStatement(query);
            rs = stmtt.executeQuery();
            rs.last();
            int size = rs.getRow();
            rs.beforeFirst();
            String movieResultString;
            if(flag==3)
            {
                movieResultString="My Bookmarks!!";
            }
            else if(flag==1)
            {
                movieResultString = "Trending Movies";
            }
            else if(flag==2)
            {
                movieResultString = "Recommended Movies";
            }
            else {
                if (size == 1)
                    movieResultString = "1 movie found on Database search";
                else if (size > 1)
                    movieResultString = size + " movies found on Database search";
                else
                    movieResultString = "No results found in Database";
            }
            Label name = new Label(movieResultString);
            name.setFont(new Font("Cambria", 20));
            name.setTextFill(Color.web("#6ac045"));
            name.setAlignment(Pos.TOP_CENTER);

            verticalTemplates.getChildren().add(name);

            while (rs.next()){
                String movieid = rs.getString("MID");
                String movieName = rs.getString("Title");
                int yearOfRelease = rs.getInt("Release_Year");
                double IMDB = rs.getDouble("Imdb_rating");
                String synopsis = rs.getString("Synopsis");
                String imageurl = rs.getString("Image_url");

                BorderPane appendTemplate = searchMovie.movieTemplate(movieid,movieName,yearOfRelease,IMDB,synopsis,imageurl);
                verticalTemplates.getChildren().add(appendTemplate);
            }
            movieResults.getChildren().add(verticalTemplates);
        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            DBUtils.closeAll(rs,stmtt,conn);
        }
        return movieResults;
    }
}