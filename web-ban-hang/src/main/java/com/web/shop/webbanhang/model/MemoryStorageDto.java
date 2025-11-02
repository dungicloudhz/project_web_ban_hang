package com.web.shop.webbanhang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemoryStorageDto {

    private Long memoryStorageId;
    @NotNull
    private String memoryStorageName;

    private Boolean isEdit = false;
}
