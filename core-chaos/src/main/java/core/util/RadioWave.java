/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package core.util;

/**
 * <p>Independent kind of 'iterator' called {@code RadioWave} that wrap an integer.</p>
 * <p>
 * <b>PS: </b>there's no setter, because it's only purpose is to 
 * increment/decrement or even {@link #consumesBy(int)} which decrement by 
 * the value in the argument of-course the sign does flip the operation.
 * </p>
 * @author rash4
 */
public final class RadioWave {
    private final int min, max, step;
    private int current;
    private boolean ascend;
    public RadioWave(int min, int max, boolean ascend, int moveBy){
        if(min >= max || moveBy <= 0) throw new IllegalArgumentException();
        this.min = min;
        this.max = max;
        this.step = moveBy;
        this.reset(ascend);
    }
    public int value(){return this.current;}
    public boolean isAscending(){return this.ascend;}
    public boolean inBounds(){
    // exclusive.. following the java way (in a sense) since it's 'in' not 'on' XD
        return this.current < this.max && this.current > this.min;
    }
    public void reset(){
        this.current = this.ascend? this.min : this.max;
    }
    public final void reset(boolean ascend){
        this.ascend = ascend;
        this.reset();
    }
    public int crement(){
        return this.current += this.ascend? +this.step : -this.step;
    }
}
