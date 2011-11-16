/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.security.server.service.ldap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
/**
 * Mock d'un itinialContext.<p>Pour utiliser il suffit de faire :
 * <pre> Properties prop = new Properties();
 * prop.put(Context.INITIAL_CONTEXT_FACTORY, "net.codjo.mock.naming.InitialContextMock");
 * prop.put(Context.PROVIDER_URL,
 * getUrl()); </pre></p>
 * <p>Dans les tests il suffit de faire ceci pour remplir l'environement :</p>
 *
 * <pre>
 * System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
 * "net.codjo.test.mock.naming.InitialContextMock");
 * new InitialContext().addToEnvironment("ejb/param/Bobo", "bobo");
 * new InitialContext().addToEnvironment("ejb/param/Bobi", "bobi");
 * </pre>
 *
 * @author $Author: crego $
 * @version $Revision: 1.10 $
 */
@SuppressWarnings({"unchecked", "StaticNonFinalField"})
public class InitialContextMock implements InitialContextFactory, Context {
    private static Map mockEnvs = Collections.synchronizedMap(new HashMap());


    public InitialContextMock() {
    }


    public Hashtable getEnvironment() throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode getEnvironment() n'est pas encore impl�ment�e.");
    }


    public Context getInitialContext(Hashtable<?, ?> environment)
          throws NamingException {
        mockEnvs.putAll(environment);
        return this;
    }


    public String getNameInNamespace() throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode getNameInNamespace() n'est pas encore impl�ment�e.");
    }


    public NameParser getNameParser(Name name) throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode getNameParser() n'est pas encore impl�ment�e.");
    }


    public NameParser getNameParser(String name) throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode getNameParser() n'est pas encore impl�ment�e.");
    }


    public Object addToEnvironment(String propName, Object propVal)
          throws NamingException {
        return put(propName, propVal);
    }


    public void bind(Name name, Object obj) throws NamingException {
        throw new java.lang.UnsupportedOperationException("La m�thode bind() n'est pas encore impl�ment�e.");
    }


    public void bind(String name, Object obj) throws NamingException {
        throw new java.lang.UnsupportedOperationException("La m�thode bind() n'est pas encore impl�ment�e.");
    }


    public void close() throws NamingException {
        mockEnvs.clear();
    }


    public Name composeName(Name name, Name prefix)
          throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode composeName() n'est pas encore impl�ment�e.");
    }


    public String composeName(String name, String prefix)
          throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode composeName() n'est pas encore impl�ment�e.");
    }


    public Context createSubcontext(Name name) throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode createSubcontext() n'est pas encore impl�ment�e.");
    }


    public Context createSubcontext(String name) throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode createSubcontext() n'est pas encore impl�ment�e.");
    }


    public void destroySubcontext(Name name) throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode destroySubcontext() n'est pas encore impl�ment�e.");
    }


    public void destroySubcontext(String name) throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode destroySubcontext() n'est pas encore impl�ment�e.");
    }


    public NamingEnumeration list(Name name) throws NamingException {
        throw new java.lang.UnsupportedOperationException("La m�thode list() n'est pas encore impl�ment�e.");
    }


    public NamingEnumeration<NameClassPair> list(String name)
          throws NamingException {
//        MockNamingEnumeration<NameClassPair> enumeration = new MockNamingEnumeration<NameClassPair>(mockEnvs, name);
//        return enumeration;
        throw new java.lang.UnsupportedOperationException("La m�thode list() n'est pas encore impl�ment�e.");
    }


    public NamingEnumeration<Binding> listBindings(Name name)
          throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode listBindings() n'est pas encore impl�ment�e.");
    }


    public NamingEnumeration<Binding> listBindings(String name)
          throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode listBindings() n'est pas encore impl�ment�e.");
    }


    public Object lookup(Name name) throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode lookup() n'est pas encore impl�ment�e.");
    }


    public Object lookup(String name) throws NamingException {
        Object obj = mockEnvs.get(name);
        if (obj instanceof NamingException) {
            throw (NamingException)obj;
        }

        return obj;
    }


    public Object lookupLink(Name name) throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode lookupLink() n'est pas encore impl�ment�e.");
    }


    public Object lookupLink(String name) throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode lookupLink() n'est pas encore impl�ment�e.");
    }


    public Object put(String name, Object val) {
        return mockEnvs.put(name, val);
    }


    public void rebind(Name name, Object obj) throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode rebind() n'est pas encore impl�ment�e.");
    }


    public void rebind(String name, Object obj) throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode rebind() n'est pas encore impl�ment�e.");
    }


    public Object removeFromEnvironment(String propName)
          throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode removeFromEnvironment() n'est pas encore impl�ment�e.");
    }


    public void rename(Name oldName, Name newName)
          throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode rename() n'est pas encore impl�ment�e.");
    }


    public void rename(String oldName, String newName)
          throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode rename() n'est pas encore impl�ment�e.");
    }


    public void unbind(Name name) throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode unbind() n'est pas encore impl�ment�e.");
    }


    public void unbind(String name) throws NamingException {
        throw new java.lang.UnsupportedOperationException(
              "La m�thode unbind() n'est pas encore impl�ment�e.");
    }
}
