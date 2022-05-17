package model;

import practica1.*;
import view.KNNView;

import java.util.LinkedList;
import java.util.List;

public class KNNModel {
    private TableWithLabels data;
    private KNNView view;

    private List<Row> dataByUser;
    private KNN dataModel;
    private Factory distanceFactory;

    public KNNModel() {
        this.dataByUser = new LinkedList<>();
        this.distanceFactory = new DistanceFactory();
        this.dataModel = new KNN(distanceFactory.getDistance(DistanceType.EUCLIDEAN));
    }
    public void setData(TableWithLabels data) {
        this.data = data;
        view.loadData();
    }
    public void setView(KNNView view) {
        this.view = view;
    }
    public List<String> getHeaderWithoutClassField() {
        return this.data.getHeader().subList(0, this.data.getHeader().size() - 1);
    }

    public List<Double> getColumn(int numCol) {
        return data.getColumAt(numCol);
    }

    public int getIndexOfHeader(String header) {
        return data.getHeader().indexOf(header);
    }

    public boolean isDataLoaded() {
        return this.data == null;
    }

    public void addNewPoint(Row newPoint) {
        this.dataByUser.add(newPoint);
    }
    public List<Row> getDataByUser() {
        return dataByUser;
    }
    public String getEstimation(List<Double> sample) {
        dataModel.train(data);
        return dataModel.estimate(sample);
    }
    public void setDistanceAlgorithm(DistanceType distance) {
        dataModel.setDistance(distanceFactory.getDistance(distance));
    }
}
