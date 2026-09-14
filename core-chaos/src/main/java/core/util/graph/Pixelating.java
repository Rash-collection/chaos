/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package core.util.graph;

/**
 *
 * @author rash4
 */
public class Pixelating implements PixelsManager<Pixelating>{
    public Pixelating(){}
    public Pixelating(int w, int h){
        this.grid = new int[Math.abs(h)][Math.abs(w)];
    }
    protected int[][] grid;
    @Override public int[][] getGrid(){return this.grid;}
    @Override public Pixelating initGrid(int w, int h){
        this.grid = new int[Math.abs(h)][Math.abs(w)];
        return this;
    }
    @Override public Pixelating initGrid(int[][] grid){
        this.grid = PixelsManager.copy(grid);
        return this;
    }
}
