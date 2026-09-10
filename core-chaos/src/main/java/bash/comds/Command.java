/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package bash.comds;

/**
 *
 * @author rash4
 */
public final class Command  implements ComRoot{
    private final Commander CONO;
    public Command(Commander cono){this.CONO = cono;}
    public Command(Commander cono, boolean para){
        this(cono);
//        this.param = para;
    }
    public Commander com(){return this.CONO;}
//    @Override public boolean hasParam(){return this.param;}
//    public Command setParam(boolean param){
//        this.param = param;
//        return this;
//    }
//    private boolean param = false;
}
