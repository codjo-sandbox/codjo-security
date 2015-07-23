/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.security.server.service.ldap;
import java.util.Map;
import net.codjo.agent.ContainerConfiguration;
import net.codjo.plugin.common.session.SessionManager;
import net.codjo.security.server.api.SecurityContextFactoryMock;
import net.codjo.security.server.api.SessionManagerMock;
import net.codjo.security.server.api.UserFactoryMock;
import net.codjo.security.server.model.ServerModelManagerMock;
import net.codjo.security.server.storage.StorageFactoryMock;
import net.codjo.test.common.LogString;
import net.codjo.test.common.matcher.JUnitMatchers;
import org.junit.Test;

import static net.codjo.security.server.service.ldap.LdapSecurityService.DEFAULT_LDAP_POSTFIX;
import static net.codjo.security.server.service.ldap.LdapSecurityService.DEFAULT_LDAP_URL;
import static net.codjo.security.server.service.ldap.LdapSecurityService.assertConfigurationIsValid;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
/**
 *
 */
public class LdapSecurityServiceTest {

    private static final String INVALID_LDAP = "ldap://anyLdap:6969";
    private static final String INVALID_POSTFIX = "@anyDomain.com";
    private static final String LDAP_URL1 = "ldap://anyLdap1:9696";
    private static final String LDAP_URL2 = "ldap://anyLdap2:9696";


    @Test
    public void test_configuration() throws Exception {
        ContainerConfiguration configuration = new ContainerConfiguration();
        configuration.setParameter(LdapSecurityService.CONFIG_LDAP_URL_DEFAULT, INVALID_LDAP);
        configuration.setParameter(LdapSecurityService.CONFIG_LDAP_POSTFIX_DEFAULT, INVALID_POSTFIX);

        LdapSecurityService securityService = createService(configuration);
        Ldap defaultLDap = securityService.getLdaps().get(LdapSecurityService.DEFAULT_LDAP);
        assertEquals(Ldap.LDAP_FACTORY, defaultLDap.getContextFactory());
        assertEquals(INVALID_LDAP, defaultLDap.getServerUrl());
        assertEquals(INVALID_POSTFIX, defaultLDap.getLoginPostFix());
    }


    @Test
    public void test_init() throws Exception {
        ContainerConfiguration configuration = new ContainerConfiguration();

        LogString log = new LogString();
        new LdapSecurityService(configuration,
                                new UserFactoryMock(),
                                new SecurityContextFactoryMock(),
                                new ServerModelManagerMock(),
                                new SessionManagerMock(log),
                                new StorageFactoryMock());

        log.assertContent("addListener(UserConnectionLifecycle)");
    }


    @Test
    public void test_init_noDb() throws Exception {
        ContainerConfiguration configuration = new ContainerConfiguration();

        LogString log = new LogString();
        new LdapSecurityService(configuration,
                                new UserFactoryMock(),
                                new SecurityContextFactoryMock(),
                                new ServerModelManagerMock(),
                                new SessionManagerMock(log),
                                new StorageFactoryMock());

        log.assertContent("addListener(UserConnectionLifecycle)");
    }


    @Test
    public void test_configuration_multi_ldap() throws Exception {
        ContainerConfiguration configuration = new ContainerConfiguration();
        configuration.setParameter(LdapSecurityService.CONFIG_LDAP_URL_DEFAULT, INVALID_LDAP);
        configuration.setParameter(LdapSecurityService.CONFIG_LDAP_POSTFIX_DEFAULT, INVALID_POSTFIX);

        configuration.setParameter(LdapSecurityService.CONFIG_OTHER_DOMAINS, "gdo2");

        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_URL_OTHER, "gdo2"),
                                   LDAP_URL1);
        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_POSTFIX_OTHER, "gdo2"),
                                   "@free.fr");

        LdapSecurityService securityService = createService(configuration);
        Map<String, Ldap> ldaps = securityService.getLdaps();

        Ldap defaultLdap = ldaps.get("default");
        assertEquals(INVALID_LDAP, defaultLdap.getServerUrl());
        assertEquals(INVALID_POSTFIX, defaultLdap.getLoginPostFix());

        Ldap otherLDap = ldaps.get("gdo2");
        assertEquals(LDAP_URL1, otherLDap.getServerUrl());
        assertEquals("@free.fr", otherLDap.getLoginPostFix());
    }


    @Test
    public void test_configuration_backup_ldap() throws Exception {
        ContainerConfiguration configuration = new ContainerConfiguration();
        configuration.setParameter(LdapSecurityService.CONFIG_LDAP_URL_DEFAULT, INVALID_LDAP);
        configuration.setParameter(LdapSecurityService.CONFIG_LDAP_POSTFIX_DEFAULT, INVALID_POSTFIX);

        configuration.setParameter(LdapSecurityService.CONFIG_BACKUP_SERVERS, "gdo2,gdo3");

        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_URL_OTHER, "gdo2"),
                                   LDAP_URL1);
        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_POSTFIX_OTHER, "gdo2"),
                                   "@free.fr");
        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_URL_OTHER, "gdo3"),
                                   LDAP_URL2);
        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_POSTFIX_OTHER, "gdo3"),
                                   "@free.fr");

        LdapSecurityService securityService = createService(configuration);
        Map<String, Ldap> ldaps = securityService.getLdaps();

        Ldap defaultLdap = ldaps.get("default");
        assertEquals(INVALID_LDAP, defaultLdap.getServerUrl());
        assertEquals(INVALID_POSTFIX, defaultLdap.getLoginPostFix());

        Ldap backup1 = ldaps.get("gdo2");
        assertEquals(LDAP_URL1, backup1.getServerUrl());
        assertEquals("@free.fr", backup1.getLoginPostFix());

        Ldap backup2 = ldaps.get("gdo3");
        assertEquals(LDAP_URL2, backup2.getServerUrl());
        assertEquals("@free.fr", backup2.getLoginPostFix());

        assertEquals(2, defaultLdap.getBackupServers().size());
        assertThat(defaultLdap.getBackupServers(), JUnitMatchers.hasItems(backup1, backup2));
    }


    @Test
    public void test_configuration_backup_ldapOtherDomains() throws Exception {
        ContainerConfiguration configuration = new ContainerConfiguration();
        configuration.setParameter(LdapSecurityService.CONFIG_LDAP_URL_DEFAULT, INVALID_LDAP);
        configuration.setParameter(LdapSecurityService.CONFIG_LDAP_POSTFIX_DEFAULT, INVALID_POSTFIX);

        configuration.setParameter(LdapSecurityService.CONFIG_OTHER_DOMAINS, "gdother");

        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_URL_OTHER, "gdother"),
                                   LDAP_URL1);
        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_POSTFIX_OTHER, "gdother"),
                                   "@free.fr");
        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_URL_OTHER, "gdoBackup"),
                                   LDAP_URL2);
        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_POSTFIX_OTHER, "gdoBackup"),
                                   "@free.fr");
        configuration.setParameter(String.format(LdapSecurityService.CONFIG_BACKUP_SERVERS_OTHER, "gdother"),
                                   "gdoBackup");

        LdapSecurityService securityService = createService(configuration);
        Map<String, Ldap> ldaps = securityService.getLdaps();

        Ldap defaultLdap = ldaps.get("default");
        assertEquals(INVALID_LDAP, defaultLdap.getServerUrl());
        assertEquals(INVALID_POSTFIX, defaultLdap.getLoginPostFix());

        Ldap other = ldaps.get("gdother");
        assertEquals(LDAP_URL1, other.getServerUrl());
        assertEquals("@free.fr", other.getLoginPostFix());

        Ldap backup = ldaps.get("gdoBackup");
        assertEquals(LDAP_URL2, backup.getServerUrl());
        assertEquals("@free.fr", backup.getLoginPostFix());

        assertEquals(1, other.getBackupServers().size());
        assertThat(other.getBackupServers(), JUnitMatchers.hasItems(backup));
    }


    @Test
    public void test_configuration_backup_hasBackup() throws Exception {
        ContainerConfiguration configuration = new ContainerConfiguration();
        configuration.setParameter(LdapSecurityService.CONFIG_LDAP_URL_DEFAULT, INVALID_LDAP);
        configuration.setParameter(LdapSecurityService.CONFIG_LDAP_POSTFIX_DEFAULT, INVALID_POSTFIX);

        configuration.setParameter(LdapSecurityService.CONFIG_BACKUP_SERVERS, "backup1");

        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_URL_OTHER, "backup1"),
                                   LDAP_URL1);
        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_POSTFIX_OTHER, "backup1"),
                                   "@free.fr");
        configuration.setParameter(String.format(LdapSecurityService.CONFIG_BACKUP_SERVERS_OTHER, "backup1"),
                                   "backup2");
        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_URL_OTHER, "backup2"),
                                   LDAP_URL2);
        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_POSTFIX_OTHER, "backup2"),
                                   "@free.fr");

        try {
            createService(configuration);
            fail();
        }
        catch (Exception e) {
            assertEquals("Le serveur backup backup1 ne peut pas avoir de backup !!!", e.getMessage());
        }
    }


    @Test
    public void test_backup_other() throws Exception {
        ContainerConfiguration configuration = new ContainerConfiguration();
        configuration.setParameter(LdapSecurityService.CONFIG_LDAP_URL_DEFAULT, INVALID_LDAP);
        configuration.setParameter(LdapSecurityService.CONFIG_LDAP_POSTFIX_DEFAULT, INVALID_POSTFIX);

        configuration.setParameter(LdapSecurityService.CONFIG_OTHER_DOMAINS, "backup1, backup2");

        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_URL_OTHER, "backup1"),
                                   LDAP_URL1);
        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_POSTFIX_OTHER, "backup1"),
                                   "@free.fr");
        configuration.setParameter(String.format(LdapSecurityService.CONFIG_BACKUP_SERVERS_OTHER, "backup1"),
                                   "backup2");
        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_URL_OTHER, "backup2"),
                                   LDAP_URL1);
        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_POSTFIX_OTHER, "backup2"),
                                   "@free.fr");

        try {
            createService(configuration);
            fail();
        }
        catch (Exception e) {
            assertEquals(
                  "Le serveur backup2 ne peut pas � la fois d�finir un autre domaine et �tre backup !!!",
                  e.getMessage());
        }
    }


    @Test
    public void test_assertConfigurationIsValidOtherLdap() throws Exception {
        ContainerConfiguration configuration = new ContainerConfiguration();
        configuration.setParameter(LdapSecurityService.CONFIG_OTHER_DOMAINS, "gdo2");
        configuration.setParameter(String.format(LdapSecurityService.CONFIG_LDAP_POSTFIX_OTHER, "gdo2"),
                                   "@free.fr");

        try {
            assertConfigurationIsValid(configuration);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                  "La configuration n�cessaire pour la connexion LDAP sur le domaine gdo2 est incompl�te : "
                  + "V�rifier la pr�sence des properties 'LdapSecurityService.ldap.gdo2.url' "
                  + "et 'LdapSecurityService.ldap.gdo2.postfix'",
                  e.getMessage());
        }
    }


    @Test
    public void test_configuration_default() throws Exception {
        ContainerConfiguration configuration = new ContainerConfiguration();

        LdapSecurityService securityService = createService(configuration);
        Ldap defaultLDap = securityService.getLdaps().get(LdapSecurityService.DEFAULT_LDAP);
        assertEquals(Ldap.LDAP_FACTORY, defaultLDap.getContextFactory());
        assertEquals(DEFAULT_LDAP_URL, defaultLDap.getServerUrl());
        assertEquals(DEFAULT_LDAP_POSTFIX, defaultLDap.getLoginPostFix());
    }


    private LdapSecurityService createService(ContainerConfiguration configuration) {
        return new LdapSecurityService(configuration,
                                       new UserFactoryMock(),
                                       new SecurityContextFactoryMock(),
                                       new ServerModelManagerMock(),
                                       new SessionManager(),
                                       new StorageFactoryMock());
    }
}
