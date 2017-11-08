package com.ClassroomPopcorn.main.windows.signUp;

import com.ClassroomPopcorn.database.signIn.dbSignUp;

import com.ClassroomPopcorn.main.windows.home.main;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class userSignUp {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public String[] status = {"","",""};

    public String[] userSignUp(){
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

        Stage loginStage = new Stage();
        loginStage.setTitle("Classroom Popcorn Signup");
        loginStage.initModality(Modality.APPLICATION_MODAL);

        BorderPane loginPane = new BorderPane();
        loginPane.setPadding(new Insets(50,20,20,20));

        HBox loginHeader = new HBox();

        loginHeader.setAlignment(Pos.BASELINE_CENTER);
        Label header = new Label("Sign Up");
        header.setFont(new Font("Cambria", 25));
        header.setTextFill(Color.web("#fff"));
        loginHeader.getChildren().add(header);

        loginPane.setTop(loginHeader);

        VBox vb = new VBox(15);
        vb.setPadding(new Insets(50,20,-20,20));

        TextField fullName = new TextField();
        fullName.setPromptText("Full Name");
        fullName.setStyle("-fx-border-radius: 100");
        fullName.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                loginPane.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

        TextField email = new TextField();
        email.setPromptText("Email Id");
        email.setStyle("-fx-border-radius: 100");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setStyle("-fx-border-radius: 100");

        PasswordField confirmPassword = new PasswordField();
        confirmPassword.setPromptText("Confirm Password");
        confirmPassword.setStyle("-fx-border-radius: 100");

        TextField imageurl = new TextField();
        imageurl.setPromptText("Image url");
        imageurl.setStyle("-fx-border-radius: 100");

        Label error = new Label();
        error.setTextFill(Color.web("red"));

        vb.getChildren().addAll(fullName, email, password, confirmPassword, imageurl, error);

        loginPane.setCenter(vb);

        HBox loginRow = new HBox();
        Button signUpButton = new Button("SignUp");
        signUpButton.setFont(new Font("Cambria", 18));
        signUpButton.setStyle("-fx-focus-color: transparent;-fx-background-color: #6ac045;");
        signUpButton.setTextFill(Color.web("#fff"));
        loginRow.getChildren().addAll(signUpButton);
        loginRow.setAlignment(Pos.BASELINE_CENTER);

        signUpButton.setOnAction(e-> {
            if (fullName.getText().isEmpty())
                error.setText("Full Name can't be empty");
            else if (imageurl.getText().isEmpty())
                error.setText("Image url can't be empty");
            else if (email.getText().isEmpty())
                error.setText("Email Id can't be empty");
            else if (!validate(email.getText()))
                error.setText("Email ID incorrect");
            else if (password.getText().isEmpty())
                error.setText("Password can't be empty");
            else if (confirmPassword.getText().isEmpty())
                error.setText("ConfirmPassword can't be empty");
            else if (!password.getText().equals(confirmPassword.getText()))
                error.setText("Password and confirm password don't match");
            else{
                status = dbSignUp.userSignUp(fullName.getText(),email.getText(),password.getText(),imageurl.getText());
                if (status[0]=="success")
                    loginStage.close();
                else
                    error.setText(status[0]);
            }
        });

        loginPane.setBottom(loginRow);

        Scene SignUpScene = new Scene(loginPane,400,450);
        SignUpScene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.ENTER),
                () -> signUpButton.fire()
        );

        SignUpScene.getStylesheets().add(main.class.getResource("../../resources/css/main.css").toExternalForm());
        loginStage.getIcons().add(new Image(getClass().getResourceAsStream("../../resources/images/ClassroomPopcorn.png")));

        loginStage.setScene(SignUpScene);
        loginStage.setResizable(false);
        loginStage.showAndWait();

        return status;
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
}
