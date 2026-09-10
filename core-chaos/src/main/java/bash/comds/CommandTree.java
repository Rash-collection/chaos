/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package bash.comds;

import java.util.HashMap;

/**
 *
 * @author rash4
 */
public final class CommandTree implements ComRoot{ 
    public CommandTree(){this.CMDS = new HashMap<>();}
    public CommandTree setCom(String name, ComRoot com){
        if(name == null || com == null){}
        else if(com instanceof Command single){
            if(single.com() == null)return this;
            this.CMDS.putIfAbsent(name, single);
        }else if(com instanceof CommandTree list){
            if(list.CMDS.isEmpty())return this;
            this.CMDS.putIfAbsent(name, list);
        }
        return this;
    }
    public void listHelp(String prefix, bash.consoles.Console cons) {
        // List the direct command for this tree/branch if it exists
        if (this.direct != null) {
            cons.println(prefix + "Direct command: " + this.direct.getClass().getSimpleName());
        }
        // List command-trees within this tree/branch
        for (String key : this.CMDS.keySet()) {
            ComRoot com = this.CMDS.get(key);
            switch (com) {
                case CommandTree commandTree -> {
                    // If it's a commandTree, recursively list it's help.
                    cons.println(prefix + "--" + key + " (SubCommand):");
                    commandTree.listHelp(prefix + "  ", cons);
                }
                // If it's a single Command (end node/leaf), just print it out.
                case Command cmd -> 
                    cons.println(prefix + "--" + key + " (Command)");
            }
        }
    }
    public boolean hasDirectCom(){return this.direct != null;}
//    @Override public boolean hasParam(){return true;}
    public CommandTree setDirectCom(Command com){
        this.direct = com;
        return this;
    }
    public String[] getList(){return this.CMDS.keySet().toArray(String[]::new);}
    public ComRoot getCom(String command){return this.CMDS.get(command);}
    public Command directCommand(){return this.direct;}
    private Command direct;
    private final HashMap<String, ComRoot> CMDS;
}
