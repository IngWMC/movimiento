package com.pichincha.movimiento.services.impl;

import com.pichincha.movimiento.clients.ClienteClient;
import com.pichincha.movimiento.clients.CuentaClient;
import com.pichincha.movimiento.dtos.ClienteDto;
import com.pichincha.movimiento.dtos.CuentaDto;
import com.pichincha.movimiento.dtos.MovimientoDto;
import com.pichincha.movimiento.exceptions.BadRequestException;
import com.pichincha.movimiento.models.Movimiento;
import com.pichincha.movimiento.repositories.IMovimientoRepository;
import com.pichincha.movimiento.services.IMovimientoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovimientoServiceImpl implements IMovimientoService {
	private static final Logger logger = LoggerFactory.getLogger(MovimientoServiceImpl.class);

	@Autowired
	private IMovimientoRepository repo;
	@Autowired
	ClienteClient clienteClient;
	@Autowired
	CuentaClient cuentaClient;
	
	@Override
	public MovimientoDto insert(MovimientoDto obj) {
		CuentaDto cuenta = cuentaClient.findByNumeroCuenta(obj.getNumeroCuenta());
		BigDecimal totalSaldoDisponible = obj.getTipoMovimiento().equals("R")
				? cuenta.getSaldoInicial().subtract(obj.getValor())
				: cuenta.getSaldoInicial().add(obj.getValor());

		if (totalSaldoDisponible.compareTo(new BigDecimal(0)) < 0)
			throw new BadRequestException("Saldo no disponible");

		cuenta.setSaldoInicial(totalSaldoDisponible);
		cuentaClient.updateCuenta(cuenta);

		Movimiento movimientoInsertar = Movimiento.builder()
				.cuentaId(cuenta.getCuentaId().toString())
				.fechaMovimiento(LocalDateTime.now())
				.tipoMovimiento(obj.getTipoMovimiento())
				.valor(obj.getValor())
				.saldo(totalSaldoDisponible).build();

		return convertToDto(repo.save(movimientoInsertar));
	}

	@Override
	public MovimientoDto update(MovimientoDto obj) {
		if (obj.getMovimientoId() == null)
			throw new BadRequestException("El campo movimeintoId es requerido.");
		/*
			validarYObtenerMovimiento(obj.getMovimientoId());
		Movimiento movimiento = clienteClient.findByClienteId(obj.getClienteId());
		obj.setNombre(cliente.getNombre());
		 */

		return null;
	}

	@Override
	public List<MovimientoDto> findAll() {
		List<Movimiento> movimientos = repo.findAll();
		return movimientos.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}

	@Override
	public MovimientoDto fintById(Integer id) {
		Movimiento movimiento = validarYObtenerMovimiento(id);
		return convertToDto(movimiento);
	}

	@Override
	public void delete(Integer id) {
		return;
	}

	private Movimiento validarYObtenerMovimiento(Integer id) {
		Optional<Movimiento> op = repo.findById(id);
		if (op.isPresent())
			return op.get();

		throw new BadRequestException("Movimiento no encontrado.");
	}

	private MovimientoDto convertToDto(Movimiento movimiento) {
		ModelMapper modelMapper = new ModelMapper();
		MovimientoDto movimientoDto = modelMapper.map(movimiento, MovimientoDto.class);
		movimientoDto.setCuenta(null);
		movimientoDto.setMovimiento(null);
		return movimientoDto;
	}

	@Override
	public List<MovimientoDto> findByFechaMovimientoYClienteId(LocalDate fechaInicio, LocalDate fechaFin, String clienteId) {
		ClienteDto cliente = clienteClient.findByClienteId(clienteId);
		CuentaDto[] cuentas = cuentaClient.findByClienteId(clienteId);

		//List<Movimiento> prueba = repo.findByFechaMovimientoYClienteId(fechaInicio, fechaFin, "1");

		List<MovimientoDto> movimientoDtos = new ArrayList<>();
		Arrays.stream(cuentas).forEach(c -> {
			List<Movimiento> movimientos = repo.findAll();
			movimientos.stream()
					.filter(m ->  m.getCuentaId().equals(c.getCuentaId().toString()) &&
							(m.getFechaMovimiento().isAfter(fechaInicio.atStartOfDay()) &&
									(m.getFechaMovimiento().isBefore(fechaFin.atStartOfDay()) || m.getFechaMovimiento().toLocalDate().isEqual(fechaFin))))
					.forEach(r -> {
						MovimientoDto movimientoDto = MovimientoDto.builder()
								.fechaMovimiento(r.getFechaMovimiento())
								.cliente(c.getNombre())
								.numeroCuenta(c.getNumeroCuenta().toString())
								.tipoCuenta(c.getTipoCuenta())
								.saldoInicial(c.getSaldoInicial())
								.estado((c.getEstado()))
								.movimiento(r.getTipoMovimiento().equals("R")
										? r.getValor().negate()
										: r.getValor())
								.saldoDisponible(r.getSaldo())
								.build();

						movimientoDtos.add(movimientoDto);
					});
		});

		return movimientoDtos;
	}
}
