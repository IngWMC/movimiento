package com.pichincha.movimiento.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//import com.fasterxml.jackson.annotation.JsonIgnore;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {

	private Integer clienteId;
	private String nombre;
	private String genero;
	private Integer edad;
	private String dni;
	private String direccion;
	private String telefono;
	private String contrasenia;
	private String estado;

}
