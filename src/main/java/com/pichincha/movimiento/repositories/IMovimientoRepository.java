package com.pichincha.movimiento.repositories;

import com.pichincha.movimiento.models.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface IMovimientoRepository extends JpaRepository<Movimiento, Integer> {

    @Query(value = "select * from movimiento m where m.fecha_movimiento BETWEEN :fechaInicio and :fechaFin AND m.cuenta_id = :cuentaId", nativeQuery = true)
    List<Movimiento> findByFechaMovimientoYClienteId (LocalDateTime fechaInicio,
                                                      LocalDateTime fechaFin,
                                                      String cuentaId);
}
