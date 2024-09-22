package com.hibernate.practice.mapper.client;

import com.hibernate.practice.dto.client.ClientUpdateDto;
import com.hibernate.practice.entity.Client;
import com.hibernate.practice.mapper.Mapper;

import java.util.Collections;

public class ClientUpdateMapper implements Mapper<ClientUpdateDto, Client> {
    @Override
    public Client mapFrom(ClientUpdateDto from) {
        return Client.builder()
                .id(from.id())
                .username(from.username())
                .password(from.password())
                .accounts(Collections.emptyList())
                .build();
    }
}
