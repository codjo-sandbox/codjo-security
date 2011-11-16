package net.codjo.security.common.login;
/**
 *
 */
public class InvalidIPHostnameException extends Exception {
    private final String ip;
    private final String hostname;


    public InvalidIPHostnameException(String ip, String hostname) {
        super("Le nom d'h�te (" + hostname + ") et l'IP (" + ip + ") associ�e ne sont pas corrects.");
        this.ip = ip;
        this.hostname = hostname;
    }


    public String getIp() {
        return ip;
    }


    public String getHostname() {
        return hostname;
    }
}
