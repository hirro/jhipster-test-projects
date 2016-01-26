package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.TestDtoPaginationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TestDtoPagination and its DTO TestDtoPaginationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TestDtoPaginationMapper {

    TestDtoPaginationDTO testDtoPaginationToTestDtoPaginationDTO(TestDtoPagination testDtoPagination);

    TestDtoPagination testDtoPaginationDTOToTestDtoPagination(TestDtoPaginationDTO testDtoPaginationDTO);
}
