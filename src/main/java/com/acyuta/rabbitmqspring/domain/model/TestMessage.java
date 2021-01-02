package com.acyuta.rabbitmqspring.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TestMessage implements Serializable {

    private Long id;

    private String content;

    private String by;
}
