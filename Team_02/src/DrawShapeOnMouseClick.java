import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

/**
 * @author abhinaw sarang
 * @created on 01-27-2020
 * @version 1.0
 * @author Rohit Kumar Singh
 * @modified on 01-28-2020
 * @version 2.0
 */
public class DrawShapeOnMouseClick extends JPanel {

	private static final long serialVersionUID = 1L;

	private String selectedShapeName;
	private String draggedShapeName = "";
	private Boolean firstPoint = false;
	private Boolean secondPoint = false;
	private Point firstPointLocation;
	private Point secondPointLocation;
	
	
	public MouseEvent dragStartEvent;
	public DrawShapes drawShapes = new DrawShapes();

	public DrawShapeOnMouseClick() {
		this.setPreferredSize(new Dimension(1600, 800));
		this.setVisible(true);
		addMouseListener(new DrawBoardMouseListener());
		addMouseMotionListener(new Abcd());
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		System.out.println("Inside paint component.....");
		try {
			super.paintComponent(graphics);
			Graphics2D graphicsDimension = (Graphics2D) graphics;
			graphicsDimension.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			if (firstPoint && secondPoint) {
				Connections objConn = new Connections();
				objConn.DotToDotConnection(graphicsDimension,
						firstPointLocation.x, firstPointLocation.y,
						secondPointLocation.x, secondPointLocation.y);
				firstPoint = false;
				secondPoint = false;
			}
			for(Point p1 : ShapeLocation.squarePoint) {
				graphicsDimension.drawRect(p1.x, p1.y, 100, 100);
			}
			for(Point p1 : ShapeLocation.circlePoint) {
				graphicsDimension.drawOval(p1.x-40, p1.y-40, 80, 80);
				graphicsDimension.fillOval(p1.x-3, p1.y-3, 6, 6);
			}
			for(Point p1 : ShapeLocation.trianglePoint) {
				graphicsDimension.drawPolygon(new int[] { p1.x - 40, p1.x, p1.x + 40 },
						new int[] { p1.y + 40, p1.y - 40, p1.y + 40 }, 3);
				
				graphicsDimension.fillOval(p1.x - 4, p1.y - 40 + 8 - 4,
						8, 8);
				graphicsDimension.fillOval(p1.x - 40 + 8 - 4,
						p1.y + 40 - 8 - 4, 8, 8);
				graphicsDimension.fillOval(p1.x + 40 - 8 - 4,
						p1.y + 40 - 8 - 4, 8, 8);
			}
			for(Lineconnection line : ShapeLocation.LinePoint) {
				//graphicsDimension.drawRect(p1.x, p1.y, 100, 100);
				graphicsDimension.drawLine(line.P1.x, line.P1.y, line.P2.x, line.P2.y);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private class DrawBoardMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent event) {
			boolean selectingSecond = firstPoint;
			for (Point point : ShapeLocation.pointsPoint) {
				if (new Rectangle2D.Double(event.getX() - 10, event.getY() - 10, 20, 20).contains(point)) {
					if(!firstPoint) {
						firstPoint = true;
						firstPointLocation = point;
					} else {
						secondPoint = true;
						secondPointLocation = point;
					}
					break;
				}
			}
			if (firstPoint && secondPoint) {
				repaint();
				// TODO
				// Add lines to list
			} else if ((!firstPoint) || (selectingSecond && !secondPoint)) {
				firstPoint = false;
				secondPoint = false;
				try {
					selectedShapeName = ClickedShape.shapeName;
					if (selectedShapeName.isEmpty() || (selectedShapeName == null)) {
						System.out.println("selectedShapeName");
						JOptionPane.showMessageDialog(null, "Please select a shape");
					} else {
						int X = event.getX();
						int Y = event.getY();
						if (selectedShapeName.equalsIgnoreCase("triangle")) {
							ShapeLocation.trianglePoint.add((new Point(X, Y)));
							ShapeLocation.pointsPoint.add(new Point(X - 4, Y - 40 + 8 - 4));
							ShapeLocation.pointsPoint.add(new Point(X - 40 + 8 - 4, Y + 40 - 8 - 4));
							ShapeLocation.pointsPoint.add(new Point(X + 40 - 8 - 4, Y + 40 - 8 - 4));
						} else if (selectedShapeName.equalsIgnoreCase("circle")) {
							ShapeLocation.circlePoint.add((new Point(X, Y)));
							ShapeLocation.pointsPoint.add(new Point(X-3, Y-3));
						} else if (selectedShapeName.equalsIgnoreCase("square")) {
							ShapeLocation.squarePoint.add((new Point(X, Y)));
						} else {
							System.out.println("Shape not selected");
						}
						repaint();
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		}
		
		@Override
		public void mousePressed(MouseEvent event) {
			System.out.println("Inside mouse pressed");
			dragStartEvent = event;
		}
		
		@Override
		public void mouseReleased(MouseEvent event) {
				try {
					if (draggedShapeName.equalsIgnoreCase("triangle")) {
						ShapeLocation.trianglePoint.add(new Point(event.getX(), event.getY()));
					} else if (draggedShapeName.equalsIgnoreCase("square")) {
						ShapeLocation.squarePoint.add(new Point(event.getX(), event.getY()));
					} else if (draggedShapeName.equalsIgnoreCase("circle")) {
						ShapeLocation.circlePoint.add(new Point(event.getX(), event.getY()));
					}
					draggedShapeName = "";
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			repaint();
		}
	}
	
	
	private class Abcd extends MouseMotionAdapter {
		@Override
		public void mouseDragged(MouseEvent event) {
			System.out.println("Inside mouse dragged");
			try {
				for (Point point : ShapeLocation.circlePoint) {
					if (new Rectangle2D.Double(dragStartEvent.getX() - 80, dragStartEvent.getY() - 80, 120, 120).contains(point)) {
						draggedShapeName = "circle";
						ShapeLocation.circlePoint.remove(point);
						break;
					}
				}
				for (Point point : ShapeLocation.trianglePoint) {
					if (new Rectangle2D.Double(dragStartEvent.getX() - 80, dragStartEvent.getY() - 80, 120, 120).contains(point)) {
						draggedShapeName = "triangle";
						ShapeLocation.trianglePoint.remove(point);
						break;
					}
				}
				for (Point point : ShapeLocation.squarePoint) {
					if (new Rectangle2D.Double(dragStartEvent.getX() - 80, dragStartEvent.getY() - 80, 120, 120).contains(point)) {
						draggedShapeName = "square";
						ShapeLocation.squarePoint.remove(point);
						break;
					}
				}

				for (Point point : ShapeLocation.pointsPoint) {
					if (new Rectangle2D.Double(dragStartEvent.getX() - 80, dragStartEvent.getY() - 80, 120, 120).contains(point)) {
						System.out.println("Removing point from circle" + point);
						ShapeLocation.pointsPoint.remove(point);
						break;
					}
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}

