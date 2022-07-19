package battleship;

class Coordinate {

    int row;
    int col;

    Coordinate() {
        this.row = 0;
        this.col = 0;
    }

    Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    Coordinate(String coordinate) {
        this.row = toRow(coordinate);
        this.col = toCol((coordinate));
    }

    private int toRow(String coordinate) {
        char rowIndex = coordinate.charAt(0);
        int row = rowIndex - 65;
        return row;
    }

    private int toCol(String coordinate) {
        int col = Integer.parseInt(coordinate.substring(1)) - 1;
        return col;
    }

    /**
     * If in a two-dimensional space c1 lies after c2, either in x- or in y-direction, then the coordinates are swapped.
     * @param c1 first coordinate
     * @param c2 second coordinate
     */
    static void reverse(Coordinate c1, Coordinate c2) {
        int c1Row = c1.row;
        int c1Col = c1.col;
        c1.row = Math.min(c1.row, c2.row);
        c1.col = Math.min(c1.col, c2.col);
        c2.row = Math.max(c1Row, c2.row);
        c2.col = Math.max(c1Col, c2.col);
    }


    /**
     * Checks whether the coordinate lies in the specified value range (A1,...,J10) => row(0,...,9), col(0,...,9).
     * @param c coordinate
     * @return true, if the coordinate lies in the specified value range
     */
    static boolean isValid(Coordinate c) {
          return 0 <= c.row && c.row <= Fleet.ROWS - 1 && 0 <= c.col && c.col <= Fleet.COLS - 1;
    }

    /**
     * Checks whether two coordinates are either in the same column (vertical alignment) or row (horizontal alignment).
     * @param c1 first coordinate
     * @param c2 second coordinate
     * @return true, if the coordinates are either in the same column or row
     */
    static boolean areDiagonal(Coordinate c1, Coordinate c2) {
        return !(areHorizontal(c1, c2) || areVertical(c1, c2));
    }

    static boolean areHorizontal(Coordinate c1, Coordinate c2) {
        return c1.row == c2.row;
    }

    static boolean areVertical(Coordinate c1, Coordinate c2) {
        return c1.col == c2.col;
    }


    static int length(Coordinate c1, Coordinate c2) {
        return (int) Math.sqrt(Math.pow(c1.row - c2.row, 2) + Math.pow(c1.col - c2.col, 2)) + 1;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}
