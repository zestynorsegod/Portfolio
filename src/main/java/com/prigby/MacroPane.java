package com.prigby;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MacroPane extends VBox {

    public MacroPane() throws IOException, URISyntaxException {
        TomTomParser ttParser = new TomTomParser();
        this.setAlignment(Pos.TOP_CENTER);
        this.setHeight(10);

        // set bg color, border rounding(??), and border insets(??)
        setBackground(new Background((
                        new BackgroundFill(Color.DEEPSKYBLUE, CornerRadii.EMPTY, Insets.EMPTY))));

        // fetch TomTom API data (geocode)
        ttParser.setQuery("boise-id"); // Temp value for testing. Eventually will use user input.
        ttParser.buildRequest();
        ttParser.parseJSON();
        String lon = ttParser.getLon() + "";
        String lat = ttParser.getLat() + "";

        // fetch NWS API data (weather)
        NWSParser nwsParser = new NWSParser(lat, lon);
        String temp = nwsParser.getTemperature();

        //XXX Top of VBOX
        HBox topBox = new HBox();
        Border topBoxBorder = new Border(new BorderStroke(Color.DEEPPINK, BorderStrokeStyle.NONE, 
                                            CornerRadii.EMPTY, new BorderWidths(10)));
        topBox.setBorder(topBoxBorder);

        Label location = new Label(ttParser.getAddress());
        location.setAlignment(Pos.TOP_LEFT);
        location.setFont(new Font("Helvetica", 28));

        topBox.getChildren().add(location);

        //XXX Middle OF VBOX
        VBox middleBox = new VBox();
        middleBox.setAlignment(Pos.TOP_CENTER);
        Border middleBoxBorder = new Border(new BorderStroke(Color.DEEPPINK, BorderStrokeStyle.NONE, 
                                            CornerRadii.EMPTY, new BorderWidths(10)));
        middleBox.setBorder(middleBoxBorder);

        Label temperature = new Label(temp + "F");
        temperature.setAlignment(Pos.CENTER);
        temperature.setFont(new Font("Helvetica", 48));

        Label shortForecast = new Label(nwsParser.getShortForecast());
        shortForecast.setFont(new Font("Helvetica", 24));
        shortForecast.setAlignment(Pos.TOP_CENTER);

        middleBox.getChildren().addAll(temperature, shortForecast);

        //XXX BOTTOM OF VBOX
        HBox bottomBox = new HBox();
        bottomBox.setPrefHeight(200);
        bottomBox.setAlignment(Pos.CENTER);

        Label info = new Label("Enter a location: ");
        info.setFont(new Font("Helvetica", 16));
        info.setAlignment(Pos.CENTER);

        // TODO add functionality to search bar
        TextField searchBar = new TextField();
        searchBar.setFont(new Font("Helvetica", 14));
        searchBar.setAlignment(Pos.CENTER_LEFT);


        bottomBox.getChildren().addAll(info, searchBar);
        
        // Add Boxes To Pane
        getChildren().addAll(topBox, middleBox, shortForecast, bottomBox);
    }
}
