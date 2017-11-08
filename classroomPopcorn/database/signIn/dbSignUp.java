package com.ClassroomPopcorn.database.signIn;

import com.ClassroomPopcorn.database.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class dbSignUp {


    public static String[] userSignUp(String fullName,String emailId,String password,String imageurl){
        Connection con = null;

        PreparedStatement stmt = null;
        String query = DBUtils.prepareInsertQuery("classroompopcorn.User", "Email_Id, Name, Password, Image_url", "?,?,?,?");

        //String updateCurrentUserQuery = DBUtils.prepareInsertQuery("classroompopcorn.currentuserlog", "username, fullName, loggedIn", "?,?,?");

        String[] status = {"ongoing","username","emailId"};

        try{
            con = DBUtils.getConnection();
            stmt = con.prepareStatement(query);
            stmt.setString(1, emailId);
            stmt.setString(2, fullName);
            stmt.setString(3, password);
            stmt.setString(4, imageurl);
            stmt.executeUpdate();
            status[0]="success";
            status[1]=fullName;
            status[2]=emailId;
        }
        catch(Exception e){
            status[0] = e.getMessage();
        }
        finally{
            DBUtils.closeStatement(stmt);
            DBUtils.closeConnection(con);
            return status;
        }
    }

}
