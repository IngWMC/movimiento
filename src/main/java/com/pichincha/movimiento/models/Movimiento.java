package com.pichincha.movimiento.models;

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
@Entity
@Table(name = "movimiento")
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movimientoId;

    @Column(name = "cuentaId", nullable = false)
    private String cuentaId;

    @Column(name = "fechaMovimiento", nullable = false)
    private LocalDateTime fechaMovimiento;

    @Column(name = "tipoMovimiento", nullable = false, length = 1)
    private String tipoMovimiento;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo;
}
