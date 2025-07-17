package org.kurokami.test.springboot.app.repositories;

import java.util.List;

import org.kurokami.test.springboot.app.models.Banco;

public interface BancoRepository {
    
    List<Banco> findAll();

    Banco findById(Long id);

    void update(Banco banco);
}
