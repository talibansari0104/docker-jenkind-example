package com.psa.SpringbootPsa.service;

import com.psa.SpringbootPsa.dto.EmployeeRequestDto;
import com.psa.SpringbootPsa.dto.EmployeeResponseDto;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    EmployeeResponseDto createUser(EmployeeRequestDto employeeRequestDto);

    EmployeeResponseDto getEmployeeById(Long id);

    List<EmployeeResponseDto> getAllEmployee(int pageNo, int pageSize, String sortBy, String sortDir);

    boolean deleteById(Long id);

    EmployeeResponseDto updateById(Long id, Map<String, Object> updates);
}
