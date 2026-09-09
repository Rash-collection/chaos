/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package core.ents;

import java.util.HashMap;
import java.util.UUID;

/**
 * <p>Global mapping of all entities in the memory</p>
 * @author rash4
 */
public final class EntityMap {
    final static HashMap<UUID, Entity> ALL_ENTITIES = new HashMap<>();
    public static UUID validRandomGID(){
        UUID neo;
        do{
            neo = UUID.randomUUID();
        }while(ALL_ENTITIES.containsKey(neo));
        return neo;
    }
    public static Entity delete(UUID GID){
        return ALL_ENTITIES.remove(GID);
    }
    public static Entity delete(Entity target){
        return ALL_ENTITIES.remove(target.getGid());
    }
}
