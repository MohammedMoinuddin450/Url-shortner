
package com.url.shortner.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClickeventDTO {
    private LocalDate clickDate;
    private Long count;
}
