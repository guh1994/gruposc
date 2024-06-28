package com.teste.api.gruposc.validator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestEntityResponse<T>
{
    private boolean success;
    private List<String> messages;
    private T entity;

}