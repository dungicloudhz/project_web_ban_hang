package com.web.shop.webbanhang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemoryStorageDto {

    private Long memoryStorageId;

    private Long memoryStorageName;

    private Boolean isEdit = false;
}
