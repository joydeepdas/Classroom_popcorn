package com.ClassroomPopcorn.database.logIn;

import com.ClassroomPopcorn.database.utils.DBUtils;
import com.ClassroomPopcorn.main.functions.get_msn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class userLoggedIn {

    public static String[] userLoggedIn() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String msn = get_msn.getMotherboardSN();
        String query = "select Last_email_Id from classroompopcorn.System where MSN ='"+msn+"';";

        String[] status = {"ongoing","username","emailId"};

        try {
            con = DBUtils.getConnection();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            rs.beforeFirst();
            while(rs.next()) {
                status[2] = rs.getString("Last_email_Id");
            }
        } catch (Exception e) {
            status[0] = e.getMessage();
        } finally {
            DBUtils.closeAll(rs, stmt, con);
            return status;
        }
    }
}
