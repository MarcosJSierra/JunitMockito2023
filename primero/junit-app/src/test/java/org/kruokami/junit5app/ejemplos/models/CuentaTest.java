package org.kruokami.junit5app.ejemplos.models;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfSystemProperties;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.kruokami.junit5app.ejemplos.exceptions.DineroInsuficienteException;

// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaTest {

    Cuenta cuenta;
    @BeforeEach
    void intiMetodoTest(){
        System.out.println("Iniciando el metodo.");
        this.cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
    }


    @AfterEach
    void tearDown(){
        System.out.println("Finalizando metodo.");
    }



    @Test
    void testNombreCuenta(){
        cuenta = new Cuenta();
        cuenta.setPersona("Andres");
        // String esperado = "Andres";
        // String real = cuenta.getPersona();
        // assertEquals(esperado, real);
        assertEquals("Andres", cuenta.getPersona());

    }



    @Test
    void testNombreCuentaConstructorAllArgs(){
        String esperado = "Andres";
        // cuenta.setPersona("Andres");
        // String esperado = "Andres";
        // String real = cuenta.getPersona();
        // assertEquals(esperado, real);
        assertNotNull(cuenta,() -> "La cuenta no puede ser nula");
        assertEquals(esperado, cuenta.getPersona(), () ->  "El nombre de la prueba no es el que se esperaba, se esperaba: " + esperado + " sin embargo fue: " + cuenta.getPersona());

    }


    // @Test
    // void testNombreCuentaConstructorAllArgsAssertTrue(){
    //     Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
    //     // cuenta.setPersona("Andres");
    //     // String esperado = "Andres";
    //     // String real = cuenta.getPersona();
    //     // assertEquals(esperado, real);
    //     assertTrue(cuenta.getPersona().equals("Andrea"));

    // }


    @Test
    void testSaldoCuenta(){
        assertEquals( 1000.12345, cuenta.getSaldo().doubleValue());
        assertNotNull(cuenta.getSaldo());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0 );
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0 );
    }

    @Test
    void testReferenciaCuenta(){
        Cuenta cuenta1 = new Cuenta("John Doe", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.9997"));
        // assertNotEquals(cuenta2, cuenta1);
        assertEquals(cuenta2, cuenta1);

    }

    @Test
    void testDebitoCuenta(){
        cuenta.debito(new BigDecimal(100));   
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }


    @Test
    void testCreditoCuenta(){
        cuenta.credito(new BigDecimal(100));   
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testDineroInsuficienteException(){
        Exception exception  = assertThrows(
            DineroInsuficienteException.class ,
            () -> {
                cuenta.debito(new BigDecimal("1500"));
            });

            String actual = exception.getMessage();
            String esperado = "Dinero Insuficiente";
            assertEquals(esperado, actual);
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testSoloWindows(){
        System.out.println("Windows");
    }


    @Test
    @EnabledOnOs({OS.LINUX, OS.WINDOWS})
    void testSoloLinuxMac(){
        System.out.println("Linux y Mac");
    }


    @Test
    @DisabledOnOs(OS.WINDOWS)
    void testNoWindows(){
        System.out.println("No Windows");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void testOnlyJava8(){
        System.out.println("Solo Java 8");
    }

    @Test
    @DisabledOnJre(JRE.JAVA_8)
    void testNotJava8(){
        System.out.println("No en Java 8");
    }

    @Test
    void getProperties(){
        Properties properties = System.getProperties();
        properties.forEach((k, v) -> System.out.println(k + ":" + v)); 
        
    }

    @Test
    @EnabledIfSystemProperty(named = "java.specification.version", matches = "17")
    void systemJavaVersion(){
        System.out.println("Coincide la version");
    }
}
