package org.kok202.dluid.application.content.design;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.shape.CubicCurve;
import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.application.common.AbstractController;

public class BlockConnectionFollower extends AbstractController {

	@FXML
	private CubicCurve cubicCurve;

	public CubicCurve createView() throws Exception{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/design/block_connection.fxml"));
		fxmlLoader.setController(this);
        return fxmlLoader.load();
	}
	
	@FXML
    protected void initialize() {
        DoubleProperty offsetX = new SimpleDoubleProperty();
        DoubleProperty offsetY = new SimpleDoubleProperty();
        DoubleProperty controllerX1 = new SimpleDoubleProperty();
        DoubleProperty controllerY1 = new SimpleDoubleProperty();
        DoubleProperty controllerX2 = new SimpleDoubleProperty();
        DoubleProperty controllerY2 = new SimpleDoubleProperty();
        offsetX.set(CanvasConstant.CUBIC_CURVE_OFFSET_X);
        offsetY.set(CanvasConstant.CUBIC_CURVE_OFFSET_Y);
        controllerX1.bind(new When(cubicCurve.startXProperty().greaterThan(cubicCurve.endXProperty())).then(-1.0).otherwise(1.0));
        controllerX2.bind(new When(cubicCurve.startXProperty().greaterThan(cubicCurve.endXProperty())).then(1.0).otherwise(-1.0));
        cubicCurve.controlX1Property().bind(Bindings.add(cubicCurve.startXProperty(), offsetX.multiply(controllerX1)));
        cubicCurve.controlX2Property().bind(Bindings.add(cubicCurve.endXProperty(), offsetX.multiply(controllerX2)));
        cubicCurve.controlY1Property().bind(Bindings.add(cubicCurve.endYProperty(), offsetY.multiply(controllerY1)));
        cubicCurve.controlY2Property().bind(Bindings.add(cubicCurve.startYProperty(), offsetY.multiply(controllerY2)));
    }

    void setVisible(boolean visible){
        cubicCurve.setVisible(visible);
    }

    void setStart(Point2D position) {
        cubicCurve.setStartX(position.getX());
        cubicCurve.setStartY(position.getY());
    }

    void setEnd(Point2D position) {
        cubicCurve.setEndX(position.getX());
        cubicCurve.setEndY(position.getY());
    }

    Point2D getStart() {
        return new Point2D(
                cubicCurve.getStartX(),
                cubicCurve.getStartY());
    }

    Point2D getEnd() {
        return new Point2D(
            cubicCurve.getEndX(),
            cubicCurve.getEndY());
    }
}
