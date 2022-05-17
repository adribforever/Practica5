package view;

import controller.KNNController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.KNNModel;
import practica1.DistanceType;
import practica1.Row;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class KNNView {
    private KNNController controller;
    private KNNModel model;
    private ComboBox comboBoxAxisX;
    private ComboBox comboBoxAxisY;
    private NumberAxis axisY;
    private NumberAxis axisX;
    private ScatterChart chart;
    private Button fileSelector;
    private ComboBox distanceAlgorithmSelector;
    private TextField newPoint;
    private Label tagVisualizer;
    private Button estimateButton;
    private boolean firstLoadOfFile;
    private List<XYChart.Series> pointsByUser;

    public KNNView() {
        this.comboBoxAxisX = new ComboBox();
        this.comboBoxAxisY = new ComboBox();
        this.axisY = new NumberAxis();
        this.axisX = new NumberAxis();
        this.chart = new ScatterChart(axisX, axisY);
        this.fileSelector = new Button("Open file");
        this.distanceAlgorithmSelector = new ComboBox();
        this.newPoint = new TextField();
        this.tagVisualizer = new Label("No tags yet");
        this.estimateButton = new Button("Estimate");
        this.pointsByUser = new LinkedList<>();
    }

    public void setController(KNNController controller) {
        this.controller = controller;
    }

    public void setModel(KNNModel model) {
        this.model = model;
    }

    public BorderPane setUp(Stage stage) {
        BorderPane knnView = new BorderPane();

        knnView = prepareLeftPart(knnView);
        knnView = prepareBottomPart(knnView);
        knnView = prepareCenterPart(knnView);
        knnView = prepareRightPart(knnView, stage);

        return knnView;
    }

    private BorderPane prepareLeftPart(BorderPane borderPane) {
        comboBoxAxisY.setOnAction(e -> {
            updateChartTitle();
            updateAxisYTitle();
            if (!firstLoadOfFile)
                updateChartValues();
        });

        VBox leftPartOrganizer = new VBox(comboBoxAxisY);
        leftPartOrganizer.setAlignment(Pos.CENTER);

        borderPane.setLeft(leftPartOrganizer);
        return borderPane;
    }

    private BorderPane prepareBottomPart(BorderPane borderPane) {
        comboBoxAxisX.setOnAction(e -> {
            updateChartTitle();
            updateAxisXTitle();
            if (!firstLoadOfFile)
                updateChartValues();
        });
        HBox bottomPartOrganizer = new HBox(comboBoxAxisX);
        bottomPartOrganizer.setAlignment(Pos.CENTER);

        borderPane.setBottom(bottomPartOrganizer);
        return borderPane;
    }

    private BorderPane prepareCenterPart(BorderPane borderPane) {
        axisY.setLabel("axisY");
        axisX.setLabel("axisX");

        chart.setTitle("ExampleChart");
        chart.setLegendVisible(false);
        chart.getData().add(new XYChart.Series<Double, Double>());

        borderPane.setCenter(chart);
        return borderPane;
    }

    private BorderPane prepareRightPart(BorderPane borderPane, Stage stage) {
        fileSelector.setOnAction(e -> {
            firstLoadOfFile = true;
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            controller.loadFile(file);
        });

        distanceAlgorithmSelector.setOnAction(e -> {
            if (!firstLoadOfFile) {
                String inputOfUser = newPoint.getText();
                DistanceType algorithm = (DistanceType) distanceAlgorithmSelector.getSelectionModel().getSelectedItem();
                controller.changeDistanceAlgorithm(algorithm);
                if (!inputOfUser.isBlank())
                    updateEstimation(inputOfUser);
            }
        });

        estimateButton.setOnAction(e -> {
            String inputOfUser = newPoint.getText();
            if (isTextFieldValid(inputOfUser)) {
                controller.addUserPoint(inputOfUser);
                XYChart.Series<Double, Double> pointByUser = new XYChart.Series();
                pointsByUser.add(pointByUser);
                chart.getData().add(pointByUser);

                String selectedInComboX = (String) comboBoxAxisX.getSelectionModel().getSelectedItem();
                String selectedInComboY = (String) comboBoxAxisY.getSelectionModel().getSelectedItem();

                int indexFieldX = model.getIndexOfHeader(selectedInComboX);
                int indexFieldY = model.getIndexOfHeader(selectedInComboY);

                updatePointsByUserInChart(indexFieldX, indexFieldY);
                updateEstimation(inputOfUser);
            }
        });

        VBox rightPartOrganizer = new VBox(fileSelector, distanceAlgorithmSelector, newPoint, tagVisualizer, estimateButton);
        rightPartOrganizer.setAlignment(Pos.CENTER);
        rightPartOrganizer.setSpacing(5.0);

        borderPane.setRight(rightPartOrganizer);
        return borderPane;
    }

    public void loadData() {
        ObservableList header = FXCollections.observableArrayList(model.getHeaderWithoutClassField());
        comboBoxAxisY.setItems(header);
        comboBoxAxisX.setItems(header);

        comboBoxAxisX.getSelectionModel().selectFirst();
        comboBoxAxisY.getSelectionModel().selectFirst();

        ObservableList<DistanceType> distanceAlgorithms = FXCollections.observableList(List.of(DistanceType.EUCLIDEAN, DistanceType.MANHATTAN));
        distanceAlgorithmSelector.setItems(distanceAlgorithms);
        distanceAlgorithmSelector.getSelectionModel().selectFirst();

        updateChartValues();

        firstLoadOfFile = false;
    }

    private void updateChartTitle() {
        String newTitle = comboBoxAxisX.getSelectionModel().getSelectedItem() + " VS " + comboBoxAxisY.getSelectionModel().getSelectedItem();
        this.chart.setTitle(newTitle);
    }

    private void updateAxisYTitle() {
        String label = comboBoxAxisY.getSelectionModel().getSelectedItem().toString();
        axisY.setLabel(label);
    }

    private void updateAxisXTitle() {
        String label = comboBoxAxisX.getSelectionModel().getSelectedItem().toString();
        axisX.setLabel(label);
    }

    private void updateChartValues() {
        String selectedInComboX = (String) comboBoxAxisX.getSelectionModel().getSelectedItem();
        String selectedInComboY = (String) comboBoxAxisY.getSelectionModel().getSelectedItem();

        int indexFieldX = model.getIndexOfHeader(selectedInComboX);
        int indexFieldY = model.getIndexOfHeader(selectedInComboY);

        List<Double> valuesAxisX = model.getColumn(indexFieldX);
        List<Double> valuesAxisY = model.getColumn(indexFieldY);

        XYChart.Series series = (XYChart.Series) chart.getData().get(0);

        series.getData().clear();
        for (int i = 0; i < valuesAxisX.size(); i++)
            series.getData().add(new XYChart.Data<>(valuesAxisX.get(i), valuesAxisY.get(i)));

        updatePointsByUserInChart(indexFieldX, indexFieldY);
    }

    private boolean isTextFieldValid(String inputOfUser) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String regex = "[0-9]+";
        String regexCompleto = regex + "," + regex + "," + regex + "," + regex;
        // Crea una alerta si el formato no es correcto
        if (!inputOfUser.matches(regexCompleto)) {
            alert.setTitle("Error ");
            alert.setHeaderText("Formato incorrecto");
            alert.setContentText("Introduce un formato tal que: 1,2,3,4");
            alert.showAndWait();
            return false;
        }
        //
        if (model.isDataLoaded()) {
            alert.setTitle("Error");
            alert.setHeaderText("No data");
            alert.setContentText("You forgot to load data!");

            alert.showAndWait();
            return false;
        }

        if (inputOfUser.isBlank()) {
            alert.setTitle("Error");
            alert.setHeaderText("Empty text field");
            alert.setContentText("You are trying to estimate a blank point!");

            alert.showAndWait();
            return false;
        }

        return true;
    }

    private void updatePointsByUserInChart(int indexOfX, int indexOfY) {
        List<Row> listOfUserPoints = model.getDataByUser();
        for (int i = 0; i < pointsByUser.size(); i++) {
            pointsByUser.get(i).getData().clear();
            Double dataOfX = listOfUserPoints.get(i).getData().get(indexOfX);
            Double dataOfY = listOfUserPoints.get(i).getData().get(indexOfY);
            XYChart.Data point = new XYChart.Data(dataOfX, dataOfY);
            pointsByUser.get(i).getData().add(point);
        }
    }

    private void updateEstimation(String inputOfUser) {
        String estimation = model.getEstimation(controller.getPointFromText(inputOfUser));
        tagVisualizer.setText(estimation);
    }
}
