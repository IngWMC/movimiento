package com.pichincha.movimiento.clients;

import com.pichincha.movimiento.dtos.ClienteDto;
import com.pichincha.movimiento.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClienteClient {
    private static final Logger logger = LoggerFactory.getLogger(ClienteClient.class);
    private static String URL = "http://localhost:7070/api/v1/clientes/";
    private final RestTemplate restTemplate;

   public ClienteClient() {
       this.restTemplate = new RestTemplate();
   }

    public ClienteDto findByClienteId(String clienteId){
        logger.info("Inicio ClienteClient ::: findByClienteId ::: " + clienteId);
        try {
            ResponseEntity<ClienteDto> response = this.restTemplate.getForEntity( URL + clienteId, ClienteDto.class);
            logger.info("Fin ClienteClient ::: findByClienteId");
            return response.getBody();
        } catch (Exception ex){
            logger.info("Error ClienteClient ::: findByClienteId");
            throw new BadRequestException("Cliente no encontrado");
        }
    }
}
