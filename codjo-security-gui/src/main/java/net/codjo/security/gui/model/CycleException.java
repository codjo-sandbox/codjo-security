package net.codjo.security.gui.model;
import net.codjo.security.common.message.Role;
import net.codjo.security.common.message.RoleComposite;
/**
 *
 */
public class CycleException extends RuntimeException {
    public CycleException(Role father, RoleComposite son) {
        super("Cycle d�tect�."
              + " Le r�le '" + father.getName() + "'"
              + " est un r�le p�re de '" + son.getName() + "'");
    }
}
