package com.hibernate.practice.service;

import com.hibernate.practice.dto.client.ClientCreateDto;
import com.hibernate.practice.dto.client.ClientReadDto;
import com.hibernate.practice.dto.client.ClientUpdateDto;
import com.hibernate.practice.entity.Client;
import com.hibernate.practice.mapper.client.ClientCreateMapper;
import com.hibernate.practice.mapper.client.ClientReadMapper;
import com.hibernate.practice.mapper.client.ClientUpdateMapper;
import com.hibernate.practice.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hibernate.practice.util.ShaUtils.sha256;

@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientReadMapper clientReadMapper;
    private final ClientCreateMapper clientCreateMapper;
    private final ClientUpdateMapper clientUpdateMapper;

    @Transactional
    public Long create(ClientCreateDto dto) {
        Client client = clientCreateMapper.mapFrom(dto);
        client.setPassword(sha256(client.getPassword()));
        return clientRepository.save(client);
    }

    @Transactional
    public void update(ClientUpdateDto dto) {
        Client client = clientUpdateMapper.mapFrom(dto);
        client.setPassword(sha256(client.getPassword()));
        clientRepository.update(client);
    }

    @Transactional
    public Optional<ClientReadDto> findById(long id) {
        return clientRepository.findById(id)
                .map(clientReadMapper::mapFrom);
    }

    @Transactional
    public List<ClientReadDto> findAll() {
        return clientRepository.findAll().stream()
                .map(clientReadMapper::mapFrom)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean delete(long id) {
        Optional<Client> maybeClient = clientRepository.findById(id);
        maybeClient.ifPresent(clientRepository::delete);
        return maybeClient.isPresent();
    }
}
