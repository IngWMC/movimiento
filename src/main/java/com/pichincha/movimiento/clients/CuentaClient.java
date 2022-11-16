package com.pichincha.movimiento.clients;

import com.pichincha.movimiento.dtos.CuentaDto;
import com.pichincha.movimiento.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CuentaClient {
    private static final Logger logger = LoggerFactory.getLogger(CuentaClient.class);
    private static String URL = "http://localhost:7071/api/v1/cuentas/";
    private final RestTemplate restTemplate;

    public CuentaClient() {
        this.restTemplate = new RestTemplate();
    }

    public CuentaDto findByNumeroCuenta(String numeroCuenta){
        logger.info("Inicio CuentaClient ::: findByNumeroCuenta ::: " + numeroCuenta);
        try {
            ResponseEntity<CuentaDto> response = this.restTemplate.getForEntity( URL + "numero-cuenta/" + numeroCuenta, CuentaDto.class);
            logger.info("Fin CuentaClient ::: findByNumeroCuenta");
            return response.getBody();
        } catch (Exception ex){
            logger.info("Error CuentaClient ::: findByNumeroCuenta");
            throw new BadRequestException("Cuenta no encontrado");
        }
    }

    public CuentaDto[] findByClienteId(String clienteId){
        logger.info("Inicio CuentaClient ::: findByClienteId ::: " + clienteId);
        try {
            ResponseEntity<CuentaDto[]> response = this.restTemplate.getForEntity( URL + "clientes/" + clienteId, CuentaDto[].class);
            logger.info("Fin CuentaClient ::: findByClienteId");
            return response.getBody();
        } catch (Exception ex){
            logger.info("Error CuentaClient ::: findByClienteId");
            throw new BadRequestException("Cuentas no encontrado");
        }
    }

    public CuentaDto updateCuenta(CuentaDto obj){
        logger.info("Inicio CuentaClient ::: updateCuenta ::: " + obj.toString());
        try {
            this.restTemplate.put( URL + obj.getCuentaId().toString() , obj);
            logger.info("Fin CuentaClient ::: updateCuenta");
            return obj;
        } catch (Exception ex){
            logger.info("Error CuentaClient ::: updateCuenta");
            throw new BadRequestException("Cuenta no encontrado");
        }
    }
}
