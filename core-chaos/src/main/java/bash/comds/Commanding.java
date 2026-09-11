/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package bash.comds;

import bash.consoles.Console;
import java.lang.ref.WeakReference;

/**
 *
 * @author rash4
 */
public abstract class Commanding implements Commander{
    private final CommandTree CMDS;
    private final WeakReference<Console> console;
    protected Commanding(Console cons){
        this.CMDS = new CommandTree();
        this.console = new WeakReference<>(cons);
    }
    @Override public final boolean execute(String command) {
        if(command == null)return false;
        this.getConsole().println(command);
        final var parts = parts(command);
        final String pt1;
        final String remainings = (parts.length > 1) ? parts[1] : null;
        final ComRoot com;
        if(parts.length > 0){
            pt1 = parts[0].toLowerCase();
            com = this.CMDS.getCom(pt1);
            if(com == null){
                this.getConsole().println(">> Command not found... \n  - write 'help' to get the main commands list.");
                return false;
            }return switch(com){
                case Command cmd -> cmd.com().execute(remainings);
                case CommandTree sub -> {
                    if(remainings == null){
                        if(sub.hasDirectCom())yield sub.directCommand().com().execute("");
                        yield false;
                    }
                    yield this.checkNest(remainings, sub);
                }
            };
        }
        return false;
    }
    protected final boolean checkNest(String command, CommandTree next){
        final var parts = parts(command);
        final String pt1;
        final String remainings = (parts.length > 1) ? parts[1] : null;
        final ComRoot com;
        if(parts.length > 0){
            pt1 = parts[0].toLowerCase();
            com = next.getCom(pt1);
            if(com == null){
                this.getConsole().println(">> Command not found... \n  - write 'help' to get the main commands list.");
                return false;
            }return switch(com){
                case Command cmd -> cmd.com().execute(remainings);
                case CommandTree tree -> {
                    if(remainings == null){
                        if(tree.hasDirectCom())yield tree.directCommand().com().execute("");
                        yield false;
                    }
                    yield this.checkNest(remainings, tree);
                }
            };
        }
        return false;
    }
    public boolean getHelp(String pleh){
        getConsole().println(">> Current MAIN commands -> list : ");
        for(String como : this.CMDS.getList()){
            getConsole().println("  --" + como + ".");
        }
        getConsole().println(">> List-End <<||");
        return true;
    }
    public boolean getCommandsList(String bleh){
        this.getConsole().println(">> All Commands -> list : ");
        this.getConsole().println(this.CMDS.treeHelp());
        this.getConsole().println(">> List-End <<||");
        return true;
    }
    public Commanding addCommand(ComRoot neo, String name){
        if(neo == null || name == null || name.isBlank())return this; // fast escape.
        switch(neo){
            case CommandTree tree-> this.CMDS.addCommand(name, tree);
            case Command cmd-> this.CMDS.addCommand(name, cmd);
        }return this;
    }
    protected Console getConsole(){return this.console.get();}
    public static String[] parts(String series){
        return (series == null ? "" : series).trim().split("\\s+", 2);
    }
}
