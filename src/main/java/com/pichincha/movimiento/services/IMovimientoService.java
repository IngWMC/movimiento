package com.pichincha.movimiento.services;

import com.pichincha.movimiento.dtos.MovimientoDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IMovimientoService extends ICRUD<MovimientoDto, Integer> {
    List<MovimientoDto> findByFechaMovimientoYClienteId(LocalDate fechaInicio, LocalDate fechaFin, String clienteId);
}
