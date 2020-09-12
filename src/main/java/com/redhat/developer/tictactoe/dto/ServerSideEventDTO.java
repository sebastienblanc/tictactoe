package com.redhat.developer.tictactoe.dto;

// Temporal class until Quarkus supports OutboundSSE
public class ServerSideEventDTO {
    private String type;
    private ServerSideEventMessage content;

    public ServerSideEventMessage getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public ServerSideEventDTO(String type, ServerSideEventMessage content) {
        this.type = type;
        this.content = content;
    }
}