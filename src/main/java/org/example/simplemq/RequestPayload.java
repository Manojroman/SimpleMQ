package org.example.simplemq;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestPayload {

    @JsonProperty("id")
    private String id;

    @JsonProperty("message")
    private String message;
}
