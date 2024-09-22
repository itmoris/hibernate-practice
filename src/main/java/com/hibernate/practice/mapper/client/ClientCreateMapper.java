package com.hibernate.practice.mapper.client;

import com.hibernate.practice.dto.client.ClientCreateDto;
import com.hibernate.practice.entity.Client;
import com.hibernate.practice.mapper.Mapper;

public class ClientCreateMapper implements Mapper<ClientCreateDto, Client> {
    @Override
    public Client mapFrom(ClientCreateDto from) {
        return Client.builder()
                .username(from.username())
                .password(from.password())
                .build();
    }
}
