package com.tuorganizacion.backend.dto;

package com.trivia.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlayerDTO {
  private Long id;
  private String username;

  public PlayerDTO(Long id, String username) {
    this.id = id;
    this.username = username;
  }
}
