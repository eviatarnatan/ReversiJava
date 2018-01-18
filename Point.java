/*
 * Eviatar Natan
 * 307851808
 */
package gameapp;

/**
 * represents a point class which contains an x and y values.
 */
public class Point {
    private int x;
    private int y;
    /**
     *creates a new point based on it's received parameters.
     *@param xValue - the x value of the point to be created.
     *@param yValue - the y value of the point to be created.
     */
    Point(int xValue, int yValue) {
          // TODO Auto-generated constructor stub
        x = xValue;
        y = yValue;
        }
    /**
     *returns the x value of the point.
     *@return x - x value of the point
     */
    int getPointX() {
        return x;
    }
    /**
     * returns the y value of the point.
     *@return y - y value of the points
     */
    int getPointY() {
        return y;
    }
    /**
     *receives an x point and sets it.
     *@param xindex - the x value to set to the point
     */
    void setPointX(int xindex) {
        x = xindex;
    }
    /**
     * receives a y point and sets it.
     *@param yindex - the y value to set to the point
     */
    void setPointY(int yindex) {
        y = yindex;
    }
}