/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package core.annots;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * This 'UnSafe' warning is mainly for risky constructors and methods <br>
 * ...especially constructors that are made specifically to construct loaded-objects.
 * </p>
 * <p><b>In short : </b>it's a FACTORY thing, hence this is a note to handle it CAREFULLY.</p>
 * @author rash4
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface UnSafe {
    String value();
}
