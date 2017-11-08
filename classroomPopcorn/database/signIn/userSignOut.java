package com.ClassroomPopcorn.database.signIn;

import com.ClassroomPopcorn.database.utils.DBUtils;

public class userSignOut {

    public static void userSignOut() {
        DBUtils.performAction("DELETE FROM `classroompopcorn`.`currentuserlog` WHERE `loggedIn`='1';");
    }

}
