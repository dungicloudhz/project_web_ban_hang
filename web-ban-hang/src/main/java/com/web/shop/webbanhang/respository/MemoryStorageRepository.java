package com.web.shop.webbanhang.respository;

import com.web.shop.webbanhang.entity.Category;
import com.web.shop.webbanhang.entity.MemoryStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemoryStorageRepository extends JpaRepository<MemoryStorage, Long> {
    Page<MemoryStorage> findByMemoryStorageNameContaining(String memoryStorageName, Pageable pageable);
}
    