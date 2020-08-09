package org.kokzoz.dluid.adapter;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class LineChartAdapter {
    @Getter
    private int lastX;
    private LineChart<Number, Number> lineChart;
    private List<XYChart.Data<Number, Number>> data;

    public LineChartAdapter(LineChart<Number, Number> lineChart) {
        this.lastX = 0;
        this.lineChart = lineChart;
        this.data = new ArrayList<>();
        lineChart.getXAxis().setAutoRanging(true);
        lineChart.getYAxis().setAutoRanging(true);
        drawChart();
    }

    public void clearChart(){
        lastX = 0;
        data.clear();
        drawChart();
    }

    public void addData(int x, double y){
        lastX = Math.max(x, lastX);
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
