package com.example;

import com.github.eduramiba.webcamcapture.drivers.NativeDriver;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main{
    public static void main(String[] args) {
        /*launch(args);*/
        Webcam.setDriver(new NativeDriver());
        Webcam webcam = Webcam.getDefault();

        System.out.println("Webcam found: " + webcam.getName());
        webcam.setViewSize(new Dimension(640, 480));
        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setMirrored(false);

        JFrame jFrame = new JFrame();
        jFrame.add(webcamPanel);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);

        while (true) {
            BufferedImage image = webcam.getImage();
            if (image != null) {
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                try {
                    Result result = new MultiFormatReader().decode(bitmap);
                    if (result.getText() != null) {
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>" + result.getText());
                        break;
                    }
                } catch (NotFoundException e) {
                    // Handle NotFoundException if needed
                }

                // Sleep for a short time to control the capture rate
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Update the WebcamPanel to display the new image
                webcamPanel.repaint();
            }
        }
    }
    /*}
    @Override
    public void start (Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/Form.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }*/
}
