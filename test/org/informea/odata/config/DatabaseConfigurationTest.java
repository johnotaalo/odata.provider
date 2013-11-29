package org.informea.odata.config;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;

import org.informea.odata.util.ToolkitUtil;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class DatabaseConfigurationTest {

    DatabaseConfiguration cfg;

    @Before
    public void before() {
        cfg = new DatabaseConfiguration();
        cfg.setType("com.mysql.jdbc.Driver");
        cfg.setHost("localhost");
        cfg.setPort(3306);
        cfg.setUser("root");
        cfg.setPassword("password");
        cfg.setDatabase("db");
    }

    @Test
    public void testDatabaseConfiguration() {
        DatabaseConfiguration cfg = new DatabaseConfiguration();
        assertNotNull(cfg);
    }

    @Test
    public void testGetType() {
        assertEquals("com.mysql.jdbc.Driver", cfg.getType());
    }

    @Test
    public void testGetHost() {
        assertEquals("localhost", cfg.getHost());
    }

    @Test
    public void testGetPort() {
        assertEquals(3306, cfg.getPort());
    }

    @Test
    public void testGetUser() {
        assertEquals("root", cfg.getUser());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password", cfg.getPassword());
    }

    @Test
    public void testGetDatabase() {
        assertEquals("db", cfg.getDatabase());
    }

    @Test
    public void testGetJDBCDriver() {
        assertEquals("com.mysql.jdbc.Driver", cfg.getJDBCDriver());
    }

    @Test
    public void testGetJDBCUrl() {
        assertEquals("jdbc:mysql://localhost:3306/db", cfg.getJDBCUrl());
        cfg.setType("X");
        assertNull(cfg.getJDBCUrl());
    }

    @Test
    public void testGetHibernateDialect() {
        assertEquals("org.hibernate.dialect.MySQLDialect", cfg.getHibernateDialect());
        cfg.setType("X");
        assertNull(cfg.getHibernateDialect());
    }

    @Test
    public void testSetType() {
        cfg.setType("test");
        assertEquals("test", cfg.getType());
    }

    @Test
    public void testSetHost() {
        cfg.setHost("host");
        assertEquals("host", cfg.getHost());
    }

    @Test
    public void testSetPort() {
        cfg.setPort(3112);
        assertEquals(3112, cfg.getPort());
    }

    @Test
    public void testSetUser() {
        cfg.setUser("cristiroma");
        assertEquals("cristiroma", cfg.getUser());
    }

    @Test
    public void testSetPassword() {
        cfg.setPassword("s3cr3t");
        assertEquals("s3cr3t", cfg.getPassword());
    }

    @Test
    public void testSetDatabase() {
        cfg.setDatabase("mysql");
        assertEquals("mysql", cfg.getDatabase());
    }

    @Test
    public void testEqualsObject() {
        DatabaseConfiguration obj = new DatabaseConfiguration();
        assertTrue(cfg.equals(cfg));
        assertFalse(cfg.equals(""));
        assertFalse(cfg.equals(null));
        assertFalse(cfg.equals(obj));

        obj.setType("com.mysql.jdbc.Driver");
        obj.setHost("localhost");
        obj.setPort(3306);
        obj.setUser("root");
        obj.setPassword("password");
        obj.setDatabase("db");

        assertTrue(cfg.equals(obj));

        obj.setType("1");
        assertFalse(cfg.equals(obj));
        obj.setType("com.mysql.jdbc.Driver");
        assertTrue(cfg.equals(obj));

        obj.setHost("1");
        assertFalse(cfg.equals(obj));
        obj.setHost("localhost");
        assertTrue(cfg.equals(obj));

        obj.setPort(1);
        assertFalse(cfg.equals(obj));
        obj.setPort(3306);
        assertTrue(cfg.equals(obj));

        obj.setUser("1");
        assertFalse(cfg.equals(obj));
        obj.setUser("root");
        assertTrue(cfg.equals(obj));

        obj.setPassword("1");
        assertFalse(cfg.equals(obj));
        obj.setPassword("password");
        assertTrue(cfg.equals(obj));

        obj.setDatabase("1");
        assertFalse(cfg.equals(obj));
        obj.setDatabase("db");
        assertTrue(cfg.equals(obj));
    }

    @Test
    public void testFromHttpRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        assertNotNull(request);
        when(request.getParameter(DatabaseConfiguration.DB_DATABASE)).thenReturn("thedatabase");
        when(request.getParameter(DatabaseConfiguration.DB_HOST)).thenReturn("localhost");
        when(request.getParameter(DatabaseConfiguration.DB_PASS)).thenReturn("password");
        when(request.getParameter(DatabaseConfiguration.DB_PORT)).thenReturn("134");
        when(request.getParameter(DatabaseConfiguration.DB_TYPE)).thenReturn("com.mysql.jdbc.Driver");
        when(request.getParameter(DatabaseConfiguration.DB_USER)).thenReturn("root");

        DatabaseConfiguration s = DatabaseConfiguration.fromHttpRequest(request);
        assertEquals("thedatabase", s.getDatabase());
        assertEquals("localhost", s.getHost());
        assertEquals("password", s.getPassword());
        assertEquals(134, s.getPort());
        assertEquals("com.mysql.jdbc.Driver", s.getType());
        assertEquals("root", s.getUser());
    }
}
