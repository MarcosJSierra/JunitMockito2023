package org.kruokami.junit5app.ejemplos.models;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BancoTest {

    @Test
    @DisplayName("Probando transferencia de dinero entre cuentas ")
    void testTransferirDineroCuentas() {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());
    }

    @Test
    @Disabled
    void testRelacionBancoCuentas() {
        fail();
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.setNombre("Banco del Estado");
        banco.addCuenta(cuenta2);
        banco.addCuenta(cuenta1);

        assertAll(
                () -> {

                    assertEquals(2, banco.getCuentas().size(), () -> "El valor no es el que se esperaba");

                },
                () -> {

                    assertEquals("Banco del Estado", cuenta1.getBanco().getNombre(), () -> "El valor no es el que se esperaba");

                },
                () -> {

                    assertTrue(banco.getCuentas().stream().filter(c -> c.getPersona().equals("Andres")).findFirst()
                            .isPresent(), () -> "El valor no es el que se esperaba");

                },
                () -> {

                    assertEquals("Andres",
                            banco.getCuentas().stream().filter(c -> c.getPersona().equals("Andres")).findFirst()
                                    .get().getPersona(), () -> "El valor no es el que se esperaba");

                },
                () -> {

                    assertTrue(banco.getCuentas().stream().anyMatch(c -> c.getPersona().equals("Andres")), () -> "El valor no es el que se esperaba");

                });
    }
}
