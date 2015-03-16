package ch.hsr.ogv.view;

import ch.hsr.ogv.view.ArrowHead.ArrowHeadType;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * 
 * @author Simon Gwerder
 *
 */
public class Arrow extends Group {
	
	private static final int INIT_WIDTH = 2;
	private double width = INIT_WIDTH;
	private double length;
	private double angle;

	private double gap = 3;
	
	private PaneBox startBox;
	private PaneBox endBox;
	
	private Box line;
	private ArrowHead head;
	private Color color = Color.BLACK;

	public Arrow(PaneBox startBox, PaneBox endBox) {
		this.startBox = startBox;
		this.endBox = endBox;
		drawArrow();
	}
	
	private void drawArrow() {
		getTransforms().clear();
		Point3D startPoint = this.startBox.getCenterPoint();
		Point3D endPoint = this.endBox.getCenterPoint();
		
		double distance = startPoint.distance(endPoint);
		this.length = distance;
		this.line = new Box(this.width, this.width, this.length);
		
		setArrowHeadType(ArrowHeadType.OPEN);
		setColor(this.color);
		
		setTranslateXYZ(startPoint.midpoint(endPoint));
		doRotate(startPoint, endPoint);
	}
	
	private void doRotate(Point3D startPoint, Point3D endPoint) {
		double pointWidth = startPoint.distance(new Point3D(endPoint.getX(), endPoint.getY(), startPoint.getZ()));
		double pointHeight = startPoint.distance(new Point3D(startPoint.getX(), endPoint.getY(), endPoint.getZ()));
		this.angle = Math.toDegrees(Math.atan(pointWidth / pointHeight));
		addRotate(this.angle);
	}
	
	public void setColor(Color color) {
		this.color = color;
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(this.color);
		material.setSpecularColor(this.color.brighter());
		this.line.setMaterial(material);
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setArrowHeadType(ArrowHeadType type) {
		getChildren().clear();
		getChildren().add(this.line);
		this.head = new ArrowHead(type, this.color);
		this.head.setTranslateZ(this.length / 2 + this.gap);
		getChildren().add(this.head);
	}
	
	public void setTranslateXYZ(Point3D point) {
		setTranslateXYZ(point.getX(), point.getY(), point.getZ());
	}
	
	public void setTranslateXYZ(double x, double y, double z) {
		getTransforms().add(new Translate(x, y, z));
	}
	
	public void addRotate(double degree) {
		getTransforms().add(new Rotate(degree, Rotate.Y_AXIS));
	}
	
	public double getWidth() {
		return this.width;
	}
	
	public double getLength() {
		return this.length;
	}
	
	public double getAngle() {
		return this.angle;
	}

	
	
}