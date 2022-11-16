package com.pichincha.movimiento.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovimientoDto {

    private Integer movimientoId;

    @NotEmpty(message = "El campo numeroCuenta es requerido.")
    private String numeroCuenta;

    private String cuentaId;

    private CuentaDto cuenta;

    private LocalDateTime fechaMovimiento;

    @NotEmpty(message = "El campo tipoMovimiento es requerido.")
    @Pattern(regexp = "^(R|D)$", message = "El campo tipoMovimiento sólo puede ser: R ó D.")
    private String tipoMovimiento;

    @DecimalMin(value = "1.0", message = "El campo valor debe tener un valor mínimo de '1.0'.")
    @Digits(integer = 10, fraction = 3, message = "El campo valor tiene un formato no válido (#####.000).")
    @NotNull(message = "El campo valor es requerido.")
    private BigDecimal valor;

    private BigDecimal saldo;

    private String cliente;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private String estado;
    private BigDecimal movimiento;
    private BigDecimal saldoDisponible;
}
