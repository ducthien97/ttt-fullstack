package com.app.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MakeMoveGameSessionRequestDTO {
    private String playerName;
    private Integer moveIndex;
}
