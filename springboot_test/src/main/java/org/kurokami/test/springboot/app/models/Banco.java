package org.kurokami.test.springboot.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Banco {
    
    private Long id;

    private String nombre;

    private Integer totalTransferencia = 0;

    
}
