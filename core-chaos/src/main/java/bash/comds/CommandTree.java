/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package bash.comds;

import java.util.HashMap;
import java.util.Map;

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
        this.CMDS.putIfAbsent(name, com);
        return this;
    }
    public CommandTree addCommand(String name, Commander commando){
        return this.addCommand(name, new Command(commando));
    }
    public CommandTree addTree(String name, Commander directCom){
        final CommandTree tree = new CommandTree();
        this.CMDS.putIfAbsent(name, tree);
        tree.direct = new Command(directCom);
        return tree; // chaining the child!! regardless of failure.
    }
    public boolean hasDirectCom(){return this.direct != null;}
    public CommandTree setDirectCom(Command com){
        this.direct = com;
        return this;
    }
    public String treeHelp() {
        StringBuilder result = new StringBuilder("Commands:\n");
        buildTreeHelp(result, "");
        return result.toString();
    }
    private void buildTreeHelp(StringBuilder result, String prefix) {
        var sorted = CMDS.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList();
        int childCount = sorted.size() + (direct != null ? 1 : 0);
        int index = 0;
        if (direct != null) {
            boolean last = ++index == childCount;
            result.append(prefix)
                  .append(last ? "└── " : "├── ")
                  .append("<direct>")
                  .append('\n');
        }
        for (var entry : sorted) {
            boolean last = ++index == childCount;
            result.append(prefix)
                  .append(last ? "└── " : "├── ")
                  .append(entry.getKey())
                  .append('\n');
            if (entry.getValue() instanceof CommandTree tree) {
                tree.buildTreeHelp(
                        result,
                        prefix + (last ? "    " : "│   ")
                );
            }
        }
    }
    public String[] getList(){return this.CMDS.keySet().toArray(String[]::new);}
    public ComRoot getCom(String command){return this.CMDS.get(command);}
    public Command directCommand(){return this.direct;}
}
