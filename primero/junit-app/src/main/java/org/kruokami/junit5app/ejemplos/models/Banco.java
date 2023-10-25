package org.kruokami.junit5app.ejemplos.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Banco {
    
    private String nombre;

    private List<Cuenta> cuentas = new ArrayList<>();

    public void transferir(Cuenta origen, Cuenta destino, BigDecimal monto){
        origen.debito(monto);
        destino.credito(monto);
    }

    public void addCuenta(Cuenta cuenta){

        cuentas.add(cuenta);
        cuenta.setBanco(this);
    }

}
