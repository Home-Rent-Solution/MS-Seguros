package com.homerentsolution.msseguros.repository;

import com.homerentsolution.msseguros.model.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguroRepository
        extends JpaRepository<Seguro, Long> {

    List<Seguro> findByTipo(String tipo);

    List<Seguro> findByIdReserva(Long idReserva);

    List<Seguro> findByTipoOrderByCoberturaDesc(String tipo);
}