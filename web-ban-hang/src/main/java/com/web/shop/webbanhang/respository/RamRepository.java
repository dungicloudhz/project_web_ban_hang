package com.web.shop.webbanhang.respository;

import com.web.shop.webbanhang.entity.Brand;
import com.web.shop.webbanhang.entity.Product;
import com.web.shop.webbanhang.entity.Ram;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RamRepository extends JpaRepository<Ram,Long> {
}
