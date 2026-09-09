/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package core.ents;

import java.util.UUID;
import static core.ents.EntityMap.ALL_ENTITIES;

/**
 *
 * @author rash4
 */
public abstract class Entity {
    private final UUID GID;
    private String name;
    @SuppressWarnings("")// no leak, REALY!
    public Entity(String name){
        if(name == null || name.isBlank()) 
            throw new IllegalArgumentException(
                    "Entity MUST has a valid name that is not null nor empty.");
        this.name = name;
        this.GID = EntityMap.validRandomGID();
        ALL_ENTITIES.put(this.GID, this); // we already did the abscent-ID.
    }
    public UUID getGid(){return this.GID;}
    public String getName(){return this.name;}
    @Override public int hashCode(){
        return GID.hashCode();
    }
    @Override public boolean equals(Object other){
        return   other == this ||
                (other instanceof Entity neo && this.GID.equals(neo.GID));
    }
}
