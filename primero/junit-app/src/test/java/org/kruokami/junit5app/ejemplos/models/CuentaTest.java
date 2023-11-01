package org.kruokami.junit5app.ejemplos.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperties;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.kruokami.junit5app.ejemplos.exceptions.DineroInsuficienteException;

// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaTest {

    Cuenta cuenta;

    private TestInfo testinfo;
    
    private TestReporter testReporter;

    @BeforeEach
    void intiMetodoTest(TestInfo testinfo, TestReporter testReporter) {
        System.out.println("Iniciando el metodo.");

        this.testinfo = testinfo;

        this.testReporter = testReporter;

        testReporter.publishEntry(" ejecutando: " + testinfo.getDisplayName() + " " + testinfo.getTestMethod().get().getName() + " con las etiquetas " + testinfo.getTags());
        this.cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finalizando metodo.");
    }

    @Tag("Cuenta")
    @Nested
    class CuentaTestNombreYSaldo {
        @Test
        void testNombreCuenta() {


            cuenta = new Cuenta();
            cuenta.setPersona("Andres");
            // String esperado = "Andres";
            // String real = cuenta.getPersona();
            // assertEquals(esperado, real);
            assertEquals("Andres", cuenta.getPersona());

        }

        @Test
        void testNombreCuentaConstructorAllArgs() {
            String esperado = "Andres";
            // cuenta.setPersona("Andres");
            // String esperado = "Andres";
            // String real = cuenta.getPersona();
            // assertEquals(esperado, real);
            assertNotNull(cuenta, () -> "La cuenta no puede ser nula");
            assertEquals(esperado, cuenta.getPersona(),
                    () -> "El nombre de la prueba no es el que se esperaba, se esperaba: " + esperado
                            + " sin embargo fue: "
                            + cuenta.getPersona());

        }

        // @Test
        // void testNombreCuentaConstructorAllArgsAssertTrue(){
        // Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        // // cuenta.setPersona("Andres");
        // // String esperado = "Andres";
        // // String real = cuenta.getPersona();
        // // assertEquals(esperado, real);
        // assertTrue(cuenta.getPersona().equals("Andrea"));

        // }

        @Tag("Cuenta")
        @Test
        void testSaldoCuenta() {
            assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
            assertNotNull(cuenta.getSaldo());
            assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        @Tag("Cuenta")
        @Test
        void testReferenciaCuenta() {
            Cuenta cuenta1 = new Cuenta("John Doe", new BigDecimal("8900.9997"));
            Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.9997"));
            // assertNotEquals(cuenta2, cuenta1);
            assertEquals(cuenta2, cuenta1);

        }

        @Tag("Banco")
        @Test
        void testDebitoCuenta() {
            cuenta.debito(new BigDecimal(100));
            assertNotNull(cuenta.getSaldo());
            assertEquals(900, cuenta.getSaldo().intValue());
            assertEquals("900.12345", cuenta.getSaldo().toPlainString());
        }


        @DisplayName("Probando Debito Cuenta Repetir!")
        @RepeatedTest(value=5, name = "{displayName} - Repetición número {currentRepetition} de {totalRepetitions}")
        void testDebitoCuentaRepetido(RepetitionInfo info) {

            if(info.getCurrentRepetition() == 3){
                System.out.println("Esta es la repetición numero 3");
            }
            cuenta.debito(new BigDecimal(100));
            assertNotNull(cuenta.getSaldo());
            assertEquals(900, cuenta.getSaldo().intValue());
            assertEquals("900.12345", cuenta.getSaldo().toPlainString());
        }

        @Test
        void testCreditoCuenta() {
            cuenta.credito(new BigDecimal(100));
            assertNotNull(cuenta.getSaldo());
            assertEquals(1100, cuenta.getSaldo().intValue());
            assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
        }

        @Test
        void testDineroInsuficienteException() {
            Exception exception = assertThrows(
                    DineroInsuficienteException.class,
                    () -> {
                        cuenta.debito(new BigDecimal("1500"));
                    });

            String actual = exception.getMessage();
            String esperado = "Dinero Insuficiente";
            assertEquals(esperado, actual);
        }
    }


    @Tag("param")
    @Nested
    class PruebasParametrizadas{


        @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
        @ValueSource(strings = {"100", "200", "300", "500", "1000"})
        void testDebitoCuentaParametrizado(String monto) {
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }



        @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
        @CsvSource({"1,100", "2,200", "3,300", "4,500", "5,1000"})
        void testDebitoCuentaParametrizadoCSV(String index, String monto) {
            System.out.println(index + " --> " + monto);
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }


        @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
        @CsvSource({"1,100,200", "2,200,250", "3,300,400", "4,500,700", "5,1000,1000"})
        void testDebitoCuentaParametrizadoCSV2(String index, String monto, String saldo) {
            System.out.println(index + " --> " + monto);
            cuenta.debito(new BigDecimal(monto));
            cuenta.setSaldo(new BigDecimal(saldo));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }



        @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
        @CsvFileSource(resources = "/data.csv")
        void testDebitoCuentaParametrizadoCSVFile(String index, String monto) {
            System.out.println(index + " --> " + monto);
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }


        @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
        @MethodSource("montoList")
        void testDebitoCuentaParametrizadoMethodSource(String monto) {
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        private static List<String> montoList(){
            return Arrays.asList("100", "200", "300", "500", "1000");
        }
    }

    @Nested
    class SistemaOperativoTest {

        @Test
        @EnabledOnOs(OS.WINDOWS)
        void testSoloWindows() {
            System.out.println("Windows");
        }

        @Test
        @EnabledOnOs({ OS.LINUX, OS.WINDOWS })
        void testSoloLinuxMac() {
            System.out.println("Linux y Mac");
        }

        @Test
        @DisabledOnOs(OS.WINDOWS)
        void testNoWindows() {
            System.out.println("No Windows");
        }

    }

    @Nested
    @DisplayName("Probando versión de Java: ")
    class JavaVersionTest {

        @Test
        @EnabledOnJre(JRE.JAVA_8)
        @DisplayName("Comprobando que sea Java 8")
        void testOnlyJava8() {
            System.out.println("Solo Java 8");
        }

        @Test
        @DisabledOnJre(JRE.JAVA_8)
        @DisplayName("Probando que no sea Java 8")
        void testNotJava8() {
            System.out.println("No en Java 8");
        }

    }

    @Nested
    class SystemProperties {

        @Test
        void getProperties() {
            Properties properties = System.getProperties();
            properties.forEach((k, v) -> System.out.println(k + ":" + v));

        }

        @Test
        @EnabledIfSystemProperty(named = "java.specification.version", matches = "17")
        void systemJavaVersion() {
            System.out.println("Coincide la version");
        }

        @Test
        void imprimirVariablesAmbiente() {
            Map<String, String> getenv = System.getenv();
            getenv.forEach((k, v) -> System.out.println(k + " = " + v));
        }

        @Test
        @EnabledIfEnvironmentVariable(named = "EXAMPLE_DB_PASSWORD", matches = "201330")
        void testByEnv() {
            System.out.println("Test env");
        }

        @Test
        void testSaldoCuentaDev() {
            boolean esDev = "8182".equals(System.getenv("CONFIG_SERVER_PORT"));
            assumeTrue(!esDev);
            assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
            assertNotNull(cuenta.getSaldo());
            assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        @Test
        void testSaldoCuentaDevParcial() {
            boolean esDev = "8182".equals(System.getenv("CONFIG_SERVER_PORT"));
            assumingThat(!esDev, () -> {

                assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
                assertNotNull(cuenta.getSaldo());
            });
            assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }
    }

    @Nested
    @Tag("timeout")
    class pruebasTimeouts{
        
        @Test
        @Timeout(5)
        void pruebaTimeout() throws InterruptedException{
            TimeUnit.SECONDS.sleep(3);
        }


        @Test
        @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
        void pruebaTimeout2() throws InterruptedException{
            TimeUnit.SECONDS.sleep(4);
        }


        @Test
        void pruebaTimeoutConAssertions() throws InterruptedException{
            assertTimeout(Duration.ofSeconds(5), ()->{
                TimeUnit.SECONDS.sleep(4);
            });
        }
    }
}
