package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.services.serviceInterfaces.PaginationService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginationImpl implements PaginationService {
    int offset = 0;
    int size = 10;

    public Pageable pagination(int offset, int size) {
        if (size > 0) {
            this.offset = offset;
            this.size = size;
            Pageable sortById = PageRequest.of(this.offset, this.size, Sort.by(Sort.Direction.ASC, "id"));
            return sortById;
        }
        return PageRequest.of(this.offset, this.size, Sort.by(Sort.Direction.ASC, "id"));
    }
}
