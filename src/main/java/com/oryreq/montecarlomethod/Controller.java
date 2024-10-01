package com.oryreq.montecarlomethod;

import com.oryreq.montecarlomethod.models.DrawData;
import com.oryreq.montecarlomethod.services.Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class realizes object so-called <strong>Controller</strong>. <br>
 * It is used to declare and manage all control elements in application like a buttons, input fields and barchart etc. <br>
 * Redirect all methods to class-service so-called <strong>Service</strong>,
 * for example, reset focus from all elements or fill histogram and table data. <br>
 *
 * @Date: 29.09.2024
 * @Author: Vsevolod @Oryreq Ashihmin
 */
public class Controller implements Initializable {

    @FXML
    private TextField numbersCount;

    @FXML
    private TextField binomialProbability;

    @FXML
    private TextField drawsCount;

    @FXML
    private Button submitButton;

    @FXML
    private BarChart<String, Integer> histogram;

    @FXML
    private TableView<DrawData> tableView;


    private Service service;


    @FXML
    protected void onSubmitButtonClick() {
        int n = Integer.parseInt(numbersCount.getText());
        double p = Double.parseDouble(binomialProbability.getText());
        int draws = Integer.parseInt(drawsCount.getText());

        var numbers = service.getNumbers(n, p, draws);
        service.buildHistogram(numbers);
        service.buildTable(numbers);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.service = new Service(histogram, tableView);

        setUnfocusToAllElements();

        numbersCount.setText("11");
        binomialProbability.setText("0.07");
        drawsCount.setText("1000");
    }

    private void setUnfocusToAllElements() {
        var fields = this.getClass().getDeclaredFields();
        service.setUnfocusToAllElements(fields, this);
    }

}