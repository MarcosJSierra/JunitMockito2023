package org.kurokami.test.springboot.app.models;

import java.math.BigDecimal;

import org.kurokami.test.springboot.app.exceptions.DineroInsuficienteException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Cuenta {

    private Long id;

    private String persona;

    private BigDecimal saldo;
    
    public void debito(BigDecimal monto){

        var nuevoSaldo = this.saldo.subtract(monto);
        if(nuevoSaldo.compareTo(BigDecimal.ZERO) < 0){
            throw new DineroInsuficienteException("Dinero insuficiente en la cuenta.");
        }  
        this.saldo= nuevoSaldo;

    }

    public void credito(BigDecimal monto){
        this.saldo = this.saldo.add(monto);
    }
}