package controller;

import model.KNNModel;
import practica1.CSV;
import practica1.DistanceType;
import practica1.Row;
import practica1.TableWithLabels;
import view.KNNView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class KNNController {
    private KNNModel model;
    private CSV csv;
    public KNNController() {
        this.csv = new CSV();
    }
    public void setModel(KNNModel model) {
        this.model = model;
    }
    public void loadFile(File file) {
        try {
            TableWithLabels table = csv.readTableWithLabels(file.getAbsolutePath());
            model.setData(table);
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }
    public void addUserPoint(String newPoint) {
        List<Double> pointDouble = getPointFromText(newPoint);
        model.addNewPoint(new Row(pointDouble));
    }
    public List<Double> getPointFromText(String text) {
        String[] pointString = text.split(",");
        List<Double> pointDouble = new LinkedList<>();
        for (String value : pointString)
            pointDouble.add(Double.parseDouble(value));
        return pointDouble;
    }

    public void changeDistanceAlgorithm(DistanceType distance) {
        model.setDistanceAlgorithm(distance);
    }
}
