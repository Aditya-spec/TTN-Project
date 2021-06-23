package com.Bootcamp.Project.Application.services.serviceInterfaces;

import org.springframework.data.domain.Pageable;

public interface PaginationService {
    public Pageable pagination(int offset, int size);
}
