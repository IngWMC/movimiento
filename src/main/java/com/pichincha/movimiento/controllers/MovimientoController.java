package com.pichincha.movimiento.controllers;

import com.pichincha.movimiento.dtos.MovimientoDto;
import com.pichincha.movimiento.services.IMovimientoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/movimientos")
public class MovimientoController {
	private static final Logger logger = LoggerFactory.getLogger(MovimientoController.class);
	@Autowired
	private IMovimientoService service;
	
	@GetMapping
	public ResponseEntity<List<MovimientoDto>> findAll(){
		logger.info("Inicio MovimientoController ::: findAll");
		List<MovimientoDto> movimientos = service.findAll();
		logger.info("Fin MovimientoController ::: findAll");
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(movimientos);
	}

	@GetMapping("reportes")
	public ResponseEntity<List<MovimientoDto>> findByFechaMovimientoYClienteId(
			@RequestParam(name = "fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
			@RequestParam(name = "fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin,
			@RequestParam(name = "clienteId")String clienteId
			){
		logger.info("Inicio MovimientoController ::: findByFechaMovimientoYClienteId");
		List<MovimientoDto> movimientos = service.findByFechaMovimientoYClienteId(fechaInicio, fechaFin, clienteId);
		logger.info("Fin MovimientoController ::: findByFechaMovimientoYClienteId");
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(movimientos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MovimientoDto> findById(@PathVariable("id") Integer id){
		logger.info("Inicio MovimientoController ::: findById ::: " + id);
		MovimientoDto movimiento= service.fintById(id);
		logger.info("Fin MovimientoController ::: findById");
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(movimiento);
	}
	
	@PostMapping
	public ResponseEntity<MovimientoDto> insert(@Valid @RequestBody MovimientoDto obj){
		logger.info("Inicio MovimientoController ::: insert ::: " + obj);
		MovimientoDto movimiento = service.insert(obj);
		logger.info("Fin MovimientoController ::: insert");
		return new ResponseEntity<MovimientoDto>(movimiento, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<MovimientoDto> update(@PathVariable("id") Integer id, @Valid @RequestBody MovimientoDto obj){
		logger.info("Inicio MovimientoController ::: update ::: " + obj);
		obj.setMovimientoId(id);
		MovimientoDto movimiento = service.update(obj);
		logger.info("Fin MovimientoController ::: update");
		return new ResponseEntity<MovimientoDto>(movimiento, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		logger.info("Inicio MovimientoController ::: delete ::: " + id);
		service.fintById(id);
		logger.info("Fin MovimientoController ::: delete");
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
