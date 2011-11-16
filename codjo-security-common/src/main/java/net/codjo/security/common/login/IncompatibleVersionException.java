package net.codjo.security.common.login;
/**
 *
 */
public class IncompatibleVersionException extends Exception {
    public IncompatibleVersionException(String serverVersion, String clientVersion) {
        super("Version client incompatible avec le serveur.\n"
              + "Vous devez utiliser un client ayant une version �gale ou sup�rieure � celle du serveur.\n"
              + "Version serveur : '" + serverVersion + "'\n" + "Version client : '" + clientVersion + "'\n\n"
              + "Merci de bien vouloir contacter ASSET-SVP pour mettre � jour votre client.");
    }


    @Override
    public String toString() {
        return getLocalizedMessage();
    }
}
