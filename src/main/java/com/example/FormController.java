package com.example;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class FormController {
    public TextField jTextFild1;
    public Label jLabal2;
    public ImageView imageView;
    Image img;
    public void btnOnAction(ActionEvent actionEvent) {
        try{
            String str = jTextFild1.getText().trim();
            String path = System.getProperty("user.dir")+"/";
            String chartset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType,ErrorCorrectionLevel>();
            hashMap.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.L);
            generateQRcode(str,path+str+".png",chartset,hashMap,200,200);

            try {
                File file = new File(str + ".png");
                String localUrl = file.toURI().toURL().toString();
                img = new Image(localUrl);
                imageView.setImage(img);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }
}
