/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package core.util.graph;

import java.awt.Shape;

/**
 * <p>An Equivalence to {@link java.awt.Graphics} as it handles pixels with minimal number of methods.</p>
 * @author rash4
 */
public interface PixelsManager<T extends PixelsManager<T>> {
    int[][] getGrid();
    T initGrid(int w, int h);
    T initGrid(int[][] grid);
    /**
     * <p>Try to never {@code Override} this method, unless the class/interface is specifically made for {@link Pixelating} purposes.</p>
     * @return the reference of this object, cast to the specified type in the declaration.
     */
    default T selfCast(){return (T)this;}
    default int rowsNumber(){return this.getGrid().length;}
    default int colsNumber(){return this.getGrid()[0].length;} // it's defended agains jagged.
    // this instantiate a copy.
    public static int[][] copy(int[][] grid){
        if(grid == null || grid.length == 0 || grid[0].length == 0)
            throw new IllegalArgumentException(
                    "Array[][] 'grid' must never be null, raws != 0 nor cols != 0");
        final int w = maxSizedCol(grid), h = grid.length;
        final var neo = new int[h][w];
        for(int row = 0; row < h; row++){
            System.arraycopy(grid[row], 0, neo[row], 0, grid[row].length);
        }return neo;
    }
    // checker for jagged array if ever countered one!
    public static int maxSizedCol(int[][] grid){
        int length = 0;
        for(var row: grid){
            length = Math.max(length, row.length);
        }
        return length;
    }
    default T fillRect(int x, int y, int w, int h, int kolor){
        final int bndW = x + w, bndH = y + h;
        for(int row = y; row < bndH; row++){
            for(int col = x; col < bndW; col++){
                this.getGrid()[row][col] = kolor;
            }
        }
        return this.selfCast();
    }
    default T fill(Shape shape, int kolor){ // so vulnerable if shape not handled/created right.
        final int rows = this.rowsNumber(), cols = this.colsNumber();
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                if(shape.contains(col, row))this.getGrid()[row][col] = kolor;
            }
        }
        return this.selfCast();
    }
    default T flip(Shape shape){ // so vulnerable if shape not handled/created right.
        final int rows = this.rowsNumber(), cols = this.colsNumber();
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                if(shape.contains(col, row))
                    this.getGrid()[row][col] = Kolor.toNegative(this.getGrid()[row][col]);
            }
        }
        return this.selfCast();
    }
}
