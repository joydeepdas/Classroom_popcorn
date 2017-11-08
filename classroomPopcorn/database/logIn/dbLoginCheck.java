package com.ClassroomPopcorn.database.logIn;

import com.ClassroomPopcorn.database.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class dbLoginCheck {

    public static String[] dbLoginCheck(String emailId, String password) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String query = DBUtils.prepareSelectQuery(" * ", "classroompopcorn.User", "Email_Id = ?  AND Password = ?");

        //String updateCurrentUserQuery = DBUtils.prepareInsertQuery("classroompopcorn.currentuserlog", "username, fullName, loggedIn", "?,?,?");

        String[] status = {"ongoing","username","emailId"};

        try {
            con = DBUtils.getConnection();
            stmt = con.prepareStatement(query);
            stmt.setString(1, emailId);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            rs.beforeFirst();
            status[0] = "";
            status[1]="";
            status[2]="";
            while(rs.next()) {
                status[0] = "success";
                status[1] = rs.getString("Name");
                status[2] = rs.getString("Email_Id");
            }
        } catch (Exception e) {
            e.printStackTrace();
            status[0] = e.getMessage();
        } finally {
            DBUtils.closeAll(rs, stmt, con);
            return status;
        }
    }
}
