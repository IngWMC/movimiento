package com.pichincha.movimiento.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaDto {

	private Integer cuentaId;
	private Integer clienteId;
	private ClienteDto cliente;
	private String nombre;
	private Integer numeroCuenta;
	private String tipoCuenta;
	private BigDecimal saldoInicial;
	private String estado;

}
