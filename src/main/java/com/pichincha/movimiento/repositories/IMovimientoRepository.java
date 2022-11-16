package com.pichincha.movimiento.repositories;

import com.pichincha.movimiento.models.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IMovimientoRepository extends JpaRepository<Movimiento, Integer> {

    @Query(value = "SELECT * FROM movimiento m WHERE (m.fecha_movimiento::date BETWEEN :fechaInicio AND :fechaFin) AND m.cuenta_id = :cuentaId", nativeQuery = true)
    List<Movimiento> findByFechaMovimientoYClienteId (@Param("fechaInicio") LocalDate fechaInicio,
                                                      @Param("fechaFin") LocalDate fechaFin,
                                                      @Param("cuentaId") String cuentaId);
}
