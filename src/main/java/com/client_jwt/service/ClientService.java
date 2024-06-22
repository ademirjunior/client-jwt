package com.client_jwt.service;

import com.client_jwt.entities.Client;
import com.client_jwt.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    public Client findById(Long id) throws Exception {
        Optional<Client> opt = clientRepository.findById(id);
        return opt.orElseThrow(()-> new Exception("Não existe cliente com id: " + id));
    }

    public Client findByEmail(String email) throws Exception {
        Optional<Client> opt = clientRepository.findByEmail(email);
        return opt.orElseThrow(()-> new Exception("Não existe cliente com id: " + email));
    }
}
