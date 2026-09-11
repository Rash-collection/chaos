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
    public Command(Commander cono){
        if(cono == null)throw new IllegalArgumentException();
        this.CONO = cono;
    }
    public Commander com(){return this.CONO;}
}
