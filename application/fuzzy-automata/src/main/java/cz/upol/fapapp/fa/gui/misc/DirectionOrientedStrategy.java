package cz.upol.fapapp.fa.gui.misc;

import cz.upol.fapapp.fa.gui.data.Direction;
import cz.upol.fapapp.fa.gui.data.PathSegment;
import javafx.geometry.Point2D;

/**
 * Strategy working such way: if the {@link Direction} of the path from X to Y
 * is d1 and from Y to newPosition is d2 is newPosition, the newPosition is
 * indicated as part of new path semgent.
 * 
 * @author martin
 *
 */
public class DirectionOrientedStrategy extends BaseSimpleSegmentStrategy {

	public DirectionOrientedStrategy() {
		super();
	}

	@Override
	protected boolean isNewSegmentBySegment(PathSegment currentSegment, Point2D newPosition, int shaking) {
		Point2D firstPoint = currentSegment.getFirstPoint();
		Point2D lastPoint = currentSegment.getLastPoint();
		Direction currentDir = currentSegment.directionOnScreen();

		return isNewSegmentByPoints(currentDir, firstPoint, lastPoint, newPosition);
	}

	public boolean isNewSegmentByPoints(Direction currentDir, Point2D firstPoint, Point2D lastPoint,
			Point2D newPosition) {
		Direction dirToNew;

		if (lastPoint != null) {
			dirToNew = Direction.compute(lastPoint, newPosition);
		} else {
			dirToNew = null;
		}

		if (dirToNew != null && currentDir != null && dirToNew != currentDir) {
			return true;
		}

		return false;
	}
}
