package com.mycompany.myapp.web.rest.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.web.rest.dto.TestDtoServiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TestDtoService and its DTO TestDtoServiceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TestDtoServiceMapper {

    TestDtoServiceDTO testDtoServiceToTestDtoServiceDTO(TestDtoService testDtoService);

    TestDtoService testDtoServiceDTOToTestDtoService(TestDtoServiceDTO testDtoServiceDTO);
}
