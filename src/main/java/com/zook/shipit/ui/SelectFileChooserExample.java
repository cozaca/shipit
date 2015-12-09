package com.zook.shipit.ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.zook.shipit.ZooKeeperStarter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SelectFileChooserExample extends Application
{

    private Text                actionStatus;
    private Stage               savedStage;
    private static final String titleTxt        = "UL Config";
    private TextArea            txtArea;
    private static final String defaultFileName = "myconfig.txt";
    private  File selectedFile;

    public static void main(String[] args)
    {

        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {

        primaryStage.setTitle(titleTxt);

        // Window label
        Label label = new Label("Select File Choosers");
        label.setTextFill(Color.DARKBLUE);
        label.setFont(Font.font("Calibri", FontWeight.BOLD, 36));
        HBox labelHb = new HBox();
        labelHb.setAlignment(Pos.CENTER);
        labelHb.getChildren().add(label);
        
     // Text area in a scrollpane and label
        Label txtAreaLabel = new Label("Enter text and save as a file:");
        txtAreaLabel.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
        txtArea = new TextArea();
        txtArea.setWrapText(true);
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(txtArea);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.setPrefHeight(250);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        VBox txtAreaVbox = new VBox(5);
        txtAreaVbox.setPadding(new Insets(5, 5, 5, 5));
        txtAreaVbox.getChildren().addAll(txtAreaLabel, scroll);

        // Buttons
        Button btn1 = new Button("Choose a file...");
        btn1.setOnAction(new SingleFcButtonListener());
        HBox buttonHb1 = new HBox(10);
        buttonHb1.setAlignment(Pos.BOTTOM_RIGHT);
        buttonHb1.getChildren().addAll(btn1);

        // Button
        Button btn2 = new Button("Save as file...");
        btn2.setOnAction(new SaveButtonListener());
        HBox buttonHb2 = new HBox(10);
        buttonHb2.setAlignment(Pos.BOTTOM_LEFT);
        buttonHb2.getChildren().addAll(btn2);

        // Status message text
        actionStatus = new Text();
        actionStatus.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
        actionStatus.setFill(Color.FIREBRICK);

        // Vbox
        VBox vbox = new VBox(30);
        vbox.setPadding(new Insets(25, 25, 25, 25));;
        vbox.getChildren().addAll(labelHb,txtAreaVbox, buttonHb1, buttonHb2, actionStatus);

        // Scene
        Scene scene = new Scene(vbox, 800, 500); // w x h
        primaryStage.setScene(scene);
        primaryStage.show();

        savedStage = primaryStage;
    }

    private class SaveButtonListener implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent e)
        {

            showSaveFileChooser();
        }
    }

    private void showSaveFileChooser()
    {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        String fileName = selectedFile.getName();
        fileChooser.setInitialFileName(selectedFile != null ? selectedFile.getAbsolutePath(): defaultFileName);
        File savedFile = fileChooser.showSaveDialog(savedStage);
        
        if (savedFile != null)
        {

            try
            {
                saveFileRoutine(savedFile);
                String fileContent = Files.toString(new File(selectedFile.getPath()), Charsets.UTF_8);
                new ZooKeeperStarter("/" + fileName, fileContent.getBytes(), selectedFile.getAbsolutePath()).start();
                
            }
            catch (IOException e)
            {

                e.printStackTrace();
                actionStatus.setText("An ERROR occurred while saving the file!" + savedFile.toString());
                return;
            }

            actionStatus.setText("File saved: " + savedFile.toString());
        }
        else
        {
            actionStatus.setText("File save cancelled.");
        }
    }

    private void saveFileRoutine(File file) throws IOException
    {
        // Creates a new file and writes the txtArea contents into it
        String txt = txtArea.getText();
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(txt);
        writer.close();
    }

    private class SingleFcButtonListener implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent e)
        {

            showSingleFileChooser();
        }
    }

    private void showSingleFileChooser()
    {

        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null)
        {
            try
            {
                txtArea.setText(Files.toString(new File(selectedFile.getPath()), Charsets.UTF_8));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }    

            actionStatus.setText("File selected: " + selectedFile.getName());
        }
        else
        {
            actionStatus.setText("File selection cancelled.");
        }
    }

}
