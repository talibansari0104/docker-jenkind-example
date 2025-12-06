package com.psa.SpringbootPsa.service.impl;

import com.psa.SpringbootPsa.dto.EmployeeRequestDto;
import com.psa.SpringbootPsa.dto.EmployeeResponseDto;
import com.psa.SpringbootPsa.entity.Employee;
import com.psa.SpringbootPsa.exception.UserAlreadyExistException;
import com.psa.SpringbootPsa.exception.UserNotFoundException;
import com.psa.SpringbootPsa.repository.EmployeeRepository;
import com.psa.SpringbootPsa.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    @Override
    public EmployeeResponseDto createUser(EmployeeRequestDto employeeRequestDto) {
        if(employeeRepository.existsByEmail(employeeRequestDto.getEmail())) {
            throw new UserAlreadyExistException("user already exist with this email " + employeeRequestDto.getEmail());
        }
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequestDto, employee);
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeResponseDto erd = new EmployeeResponseDto();
        BeanUtils.copyProperties(savedEmployee, erd);
        return erd;
    }

    @Override
    public EmployeeResponseDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not exist with this id"));
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
        BeanUtils.copyProperties(employee, employeeResponseDto);
        return employeeResponseDto;
    }

    @Override
    public List<EmployeeResponseDto> getAllEmployee(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")? Sort.by(Sort.Direction.ASC, sortBy) : Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Employee> page = employeeRepository.findAll(pageable);
        List<Employee> employees = page.getContent();
        System.out.println(page.getNumber());
        System.out.println(page.getSize());
        System.out.println(page.getTotalPages());
        System.out.println(page.getTotalElements());
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getPageSize());
        return employees.stream().map(employee ->new EmployeeResponseDto(employee.getId(), employee.getName(), employee.getEmail(), employee.getMobile())).toList();
    }

    @Override
    public boolean deleteById(Long id) {
        if(!employeeRepository.existsById(id)) {
            throw new UserNotFoundException("user not exist with this id " + id);

        }

        employeeRepository.deleteById(id);
        return true;
    }

    @Override
    public EmployeeResponseDto updateById(Long id, Map<String, Object> updates) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not present with this id " + id));
        updates.entrySet().forEach(entry->{
            switch (entry.getKey()){
                case "name" : employee.setName((String) entry.getValue());
                break;
                case "email" : employee.setEmail((String) entry.getValue());
                break;
                case "mobile" : employee.setMobile((String) entry.getValue());
                break;
                default:
                    throw new IllegalArgumentException("update is not allowed for this value..");
            }
        });
        Employee save = employeeRepository.save(employee);
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
        BeanUtils.copyProperties(save, employeeResponseDto);
        return employeeResponseDto;
    }
}
