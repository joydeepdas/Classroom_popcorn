package com.ClassroomPopcorn.main.functions;

import com.ClassroomPopcorn.database.getMovies.movieDetails;
import com.ClassroomPopcorn.database.logIn.userLoggedIn;
import com.ClassroomPopcorn.database.signIn.userSignOut;
import com.ClassroomPopcorn.database.utils.DBUtils;
import com.ClassroomPopcorn.main.windows.login.userLogin;
import com.ClassroomPopcorn.main.windows.movie.ClassNameHere;
import com.ClassroomPopcorn.main.windows.movie.ImagePopup;
import com.ClassroomPopcorn.main.windows.signUp.userSignUp;
import com.ClassroomPopcorn.main.functions.*;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class header {

    public static BorderPane header(){
        BorderPane headerlayout = new BorderPane();
        headerlayout.setPadding(new Insets(20,30,0,30));
        headerlayout.setStyle("-fx-background-color: #1d1d1d;");

        Label label = new Label("Welcome to Classroom Popcorn");
        label.setFont(new Font("Cambria", 32));
        label.setTextFill(Color.web("#ededed"));
        headerlayout.setLeft(label);

        HBox headerVB = new HBox(10);
        headerVB.setPadding(new Insets(6,10,0,0));

        String msn = get_msn.getMotherboardSN(), dummy = null;

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "Select MSN from classroompopcorn.System where MSN ='"+msn+"';";

        try{
            con = DBUtils.getConnection();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            rs.beforeFirst();
            while(rs.next())
            {
                dummy = rs.getString("MSN");
            }
        }
        catch(SQLException sql){
            sql.printStackTrace();
        }
        finally{
            DBUtils.closeStatement(stmt);
            DBUtils.closeConnection(con);
        }
        if(dummy==null){
            String query2 = DBUtils.prepareInsertQuery("classroompopcorn.System","MSN, Last_email_Id","?,?");
            try{
                con = DBUtils.getConnection();
                stmt = con.prepareStatement(query2);
                stmt.setString(1, msn);
                stmt.setString(2, null);
                stmt.executeUpdate();
            }
            catch(SQLException sql){
                sql.printStackTrace();
            }
            finally{
                DBUtils.closeStatement(stmt);
                DBUtils.closeConnection(con);
            }
        }

        Label username = GlyphsDude.createIconLabel( FontAwesomeIcon.USER,
                "User",
                "16",
                "18",
                ContentDisplay.LEFT );
        username.setFont(new Font("Cambria", 20));
        username.setTextFill(Color.web("#ededed"));

        Label login = GlyphsDude.createIconLabel( FontAwesomeIcon.SIGN_IN,
                "Login |",
                "16",
                "18",
                ContentDisplay.LEFT );
        login.setFont(new Font("Cambria", 20));
        login.setTextFill(Color.web("#ededed"));

        Label register = GlyphsDude.createIconLabel( FontAwesomeIcon.USER_PLUS,
                "Register",
                "16",
                "18",
                ContentDisplay.LEFT );
        register.setFont(new Font("Cambria", 20));
        register.setTextFill(Color.web("#ededed"));

        Label logout = GlyphsDude.createIconLabel( FontAwesomeIcon.SIGN_OUT,
                "Log Out",
                "16",
                "18",
                ContentDisplay.LEFT );
        logout.setFont(new Font("Cambria", 20));
        logout.setTextFill(Color.web("#ededed"));

        login.setOnMouseClicked(e->{
            userLogin ob = new userLogin();
            String[] status = ob.userLogin();

            if (status[0].equals("success")){
                username.setText(status[1]);
                Connection conn = null;
                PreparedStatement stmtt = null;
                String query1 = "update classroompopcorn.System SET Last_email_Id ='"+status[2]+"' where MSN = '"+msn+"';";
                try{
                    conn = DBUtils.getConnection();
                    stmtt = conn.prepareStatement(query1);
                    stmtt.executeUpdate();
                }
                catch(SQLException sql){
                    sql.printStackTrace();
                }
                finally{
                    DBUtils.closeStatement(stmtt);
                    DBUtils.closeConnection(conn);
                }
                headerVB.getChildren().clear();
                headerVB.getChildren().addAll(username,logout);
            }
            else {
                headerVB.getChildren().clear();
                headerVB.getChildren().addAll(login,register);
            }
        });

        register.setOnMouseClicked(e->{
            userSignUp ob = new userSignUp();
            String[] status = ob.userSignUp();

            ClassNameHere.infoBox("Successfully Registered!!", "MockPopCorn");
            //if (status[0].equals("success")){
//                username.setText(status[1]);
//                headerVB.getChildren().clear();
//                headerVB.getChildren().addAll(username,logout);
            //}
            headerVB.getChildren().clear();
            headerVB.getChildren().addAll(login,register);

        });

        logout.setOnMouseClicked(e-> {
            headerVB.getChildren().clear();
            headerVB.getChildren().addAll(login,register);
            Connection conn = null;
            PreparedStatement stmtt = null;
            String query3 = "update classroompopcorn.System set  Last_email_Id = null where MSN = '"+msn+"';";
            try{
                conn = DBUtils.getConnection();
                stmtt = conn.prepareStatement(query3);
                stmtt.executeUpdate();
            }
            catch(SQLException sql){
                sql.printStackTrace();
            }
            finally{
                DBUtils.closeStatement(stmtt);
                DBUtils.closeConnection(conn);
            }
            //movieDetails.movieDetails("Z","",0);
        });

        String[] status = userLoggedIn.userLoggedIn();
        String query4 = "select Last_email_Id from classroompopcorn.system where MSN='"+msn+"';";
        Connection conn6 = null;
        PreparedStatement stmtt6 = null;
        ResultSet rs6 = null;
        String dummy1=null;
        try{
            conn6 = DBUtils.getConnection();
            stmtt6 = conn6.prepareStatement(query4);
            rs6 = stmtt6.executeQuery();
            rs6.beforeFirst();
            while(rs6.next())
            {
                dummy1 = rs6.getString("Last_email_Id");
            }
        }
        catch(SQLException sql){
            sql.printStackTrace();
        }
        finally{
            DBUtils.closeStatement(stmtt6);
            DBUtils.closeConnection(conn6);
        }
        if (status[0]=="success") {
            username.setText(status[1]);

            headerVB.getChildren().clear();
            headerVB.getChildren().addAll(username,logout);
        }
        else if(dummy1!=null)
            {
                String query5 = "select Name,Image_url from classroompopcorn.user where Email_Id='"+dummy1+"';";
                String dummy2=null,img_url="";
                try{
                    conn6 = DBUtils.getConnection();
                    stmtt6 = conn6.prepareStatement(query5);
                    rs6 = stmtt6.executeQuery();
                    rs6.beforeFirst();
                    while(rs6.next())
                    {
                        dummy2 = rs6.getString("Name");
                        img_url = rs6.getString("Image_url");
                    }
                }
                catch(SQLException sql){
                    sql.printStackTrace();
                }
                finally{
                    DBUtils.closeStatement(stmtt6);
                    DBUtils.closeConnection(conn6);
                }
                final String img_url1 = img_url;
                username.setText(dummy2);
                username.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        ImagePopup obj = new ImagePopup();
                        obj.imagePopupWindowShow(img_url1);
                    }
                });
                headerVB.getChildren().clear();
                headerVB.getChildren().addAll(username,logout);

            }
        else {
            headerVB.getChildren().clear();
            headerVB.getChildren().addAll(login,register);
        }

        headerlayout.setRight(headerVB);

        return  headerlayout;
    }
}
