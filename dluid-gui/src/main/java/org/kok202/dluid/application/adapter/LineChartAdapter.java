package org.kok202.dluid.application.adapter;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class LineChartAdapter {
    private LineChart<Number, Number> lineChart;
    private List<XYChart.Data<Number, Number>> data;

    public LineChartAdapter(LineChart<Number, Number> lineChart) {
        this.lineChart = lineChart;
        this.data = new ArrayList<>();
        lineChart.getXAxis().setAutoRanging(true);
        lineChart.getYAxis().setAutoRanging(false);
        drawChart();
    }

    public void clearChart(){
        data.clear();
        drawChart();
    }

    public void appendData(int x, double y){
        data.add(new XYChart.Data<>(x, y));
        drawChart();
    }

    public void drawChart(){
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.getData().addAll(data);
        lineChart.getData().clear();
        lineChart.getData().add(series);
    }
}
