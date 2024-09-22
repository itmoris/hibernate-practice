package com.hibernate.practice.mapper.client;

import com.hibernate.practice.dto.client.ClientReadDto;
import com.hibernate.practice.entity.Client;
import com.hibernate.practice.mapper.Mapper;

public class ClientReadMapper implements Mapper<Client, ClientReadDto> {

    @Override
    public ClientReadDto mapFrom(Client from) {
        return new ClientReadDto(from.getId(), from.getUsername());
    }
}
