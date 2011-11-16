package net.codjo.security.server.api;
import net.codjo.agent.ContainerConfiguration;
import net.codjo.agent.Service;
import net.codjo.agent.ServiceException;
import org.apache.log4j.Logger;
/**
 * Service s�curit�.
 *
 * <p>Cette classe int�gre le composant s�curit� dans le m�canisme des services JADE. Elle permet de fournir un {@link
 * SecurityServiceHelper} aux agents n�cessitant une gestion des droits. </p>
 *
 * NB : ne doit pas �tre utilis� directement
 *
 * @see net.codjo.security.server.api.SecurityServiceHelper
 */
public abstract class SecurityService implements Service {
    private final Logger log = Logger.getLogger(SecurityService.class.getName());


    public String getName() {
        return SecurityServiceHelper.NAME;
    }


    public void boot(ContainerConfiguration containerConfiguration) throws ServiceException {
        log.info("Activation du service Security : " + getClass());
    }
}
