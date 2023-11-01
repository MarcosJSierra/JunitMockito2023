package org.kruokami.junit5app.ejemplos.models;

import java.math.BigDecimal;

import org.kruokami.junit5app.ejemplos.exceptions.DineroInsuficienteException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Cuenta {

    public Cuenta(String persona, BigDecimal saldo){
        this.saldo = saldo;
        this.persona = persona;
    }
    
    private Banco banco; 
    
    private String persona;
    
    private BigDecimal saldo;


    public void debito(BigDecimal monto){
        BigDecimal nuevoSaldo = this.saldo.subtract(monto);
        if(nuevoSaldo.compareTo(BigDecimal.ZERO) < 0){
            throw new DineroInsuficienteException("Dinero Insuficiente");
        }
        this.saldo = nuevoSaldo;
    }

    public void credito(BigDecimal monto){
        this.saldo = this.saldo.add(monto);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cuenta other = (Cuenta) obj;
        if (persona == null) {
            if (other.persona != null)
                return false;
        } else if (!persona.equals(other.persona))
            return false;
        if (saldo == null) {
            if (other.saldo != null)
                return false;
        } else if (!saldo.equals(other.saldo))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((persona == null) ? 0 : persona.hashCode());
        result = prime * result + ((saldo == null) ? 0 : saldo.hashCode());
        return result;
    }

    

}
