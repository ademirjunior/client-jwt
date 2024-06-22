package com.client_jwt.resource;

import com.client_jwt.entities.Client;
import com.client_jwt.request.ClientRequest;
import com.client_jwt.response.ClientResponse;
import com.client_jwt.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "client")
public class ClientResource {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponse> save(ClientRequest clientRequest) {
        Client client = new Client();
        BeanUtils.copyProperties(clientRequest, client);
        clientService.save(client);

        ClientResponse clientResponse = new ClientResponse();
        BeanUtils.copyProperties(client, clientResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientResponse);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> findAll() {
        List<Client> clients = clientService.findAll();
        List<ClientResponse> clientResponseList = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        clients.forEach(client -> clientResponseList.add(mapper.map(client, ClientResponse.class)));
        return ResponseEntity.ok(clientResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable(name = "id") Long id) {
        Client client = null;
        try {
            client = clientService.findById(id);
            ClientResponse clientResponse = new ClientResponse();
            BeanUtils.copyProperties(client, clientResponse);
            return ResponseEntity.ok().body(clientResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ClientResponse.builder().error(e.getMessage()).build());
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClientResponse> findByEmail(@PathVariable(name = "email") String email) {
        Client client = null;
        try {
            client = clientService.findByEmail(email);
            ClientResponse clientResponse = new ClientResponse();
            BeanUtils.copyProperties(client, clientResponse);
            return ResponseEntity.ok().body(clientResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ClientResponse.builder().error(e.getMessage()).build());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable(name = "id") Long id, @RequestBody ClientRequest clientRequest) {
        try {
            Client client = clientService.findById(id);
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setSkipNullEnabled(true);
            mapper.map(clientRequest, client);
            client.setId(id);
            clientService.save(client);
            ClientResponse clientResponse = new ClientResponse();
            BeanUtils.copyProperties(client, clientResponse);
            return ResponseEntity.status(HttpStatus.OK).body(clientResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ClientResponse.builder().error(e.getMessage()).build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long id) {
        try {
            clientService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(ClientResponse.builder().error("Clint does not exist or invalid " + id).build());
        }
    }
}
