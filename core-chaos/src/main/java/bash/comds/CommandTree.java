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
    private Command direct;
    private final HashMap<String, ComRoot> CMDS;
    public CommandTree(){this.CMDS = new HashMap<>();}
    public CommandTree addCommand(String name, ComRoot com){
        if(name == null || com == null)return this;
        else if(com instanceof Command single)
            this.CMDS.putIfAbsent(name, single);
        else if(com instanceof CommandTree list)
            this.CMDS.putIfAbsent(name, list);
        return this;
    }
    /**
     * <p>Add new singleton {@link Command} instance.</p>
     * @param name
     * @param commando
     * @return 
     */
    public CommandTree addCommand(String name, Commander commando){
        return this.addCommand(name, new Command(commando));
    }
    // need checking
    public CommandTree addTree(String name, Commander directCom){
        final CommandTree tree = new CommandTree();
        if(tree == this.CMDS.putIfAbsent(name, tree)){
            tree.direct = new Command(directCom);
            return tree;
        }
        return this;
    }
    public boolean hasDirectCom(){return this.direct != null;}
    public CommandTree setDirectCom(Command com){
        this.direct = com;
        return this;
    }
    public String[] getList(){return this.CMDS.keySet().toArray(String[]::new);}
    public ComRoot getCom(String command){return this.CMDS.get(command);}
    public Command directCommand(){return this.direct;}
}
