/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package core;

import bash.comds.Command;
import bash.comds.CommandTree;
import bash.comds.Commanding;
import bash.consoles.Console;

/**
 * <b>Test class for this jar is not executable.</b>
 * @author rash4
 */
public class CoreChaos {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        final var conso = new bash.consoles.Console();
        conso.initConsole();
        final GoMand cmd = new GoMand(conso);
        conso.setCommando(cmd);
        cmd.addCommand(new CommandTree().setDirectCom(new Command(help->{return cmd.getHelp("");})), "help");
        
    }
}
