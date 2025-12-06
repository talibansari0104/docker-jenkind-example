package com.psa.SpringbootPsa.controller;
import com.psa.SpringbootPsa.dto.ApiResponse;
import com.psa.SpringbootPsa.dto.EmployeeRequestDto;
import com.psa.SpringbootPsa.dto.EmployeeResponseDto;
import com.psa.SpringbootPsa.exception.UserAlreadyExistException;
import com.psa.SpringbootPsa.exception.UserNotFoundException;
import com.psa.SpringbootPsa.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

//    http://localhost:8080/api/v1/employee/create
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createUser(@Valid @RequestBody EmployeeRequestDto employeeRequestDto){
        try {
            EmployeeResponseDto user = employeeService.createUser(employeeRequestDto);
            ApiResponse<EmployeeResponseDto> apiResponse = ApiResponse.<EmployeeResponseDto>builder()
                    .success(true)
                    .message("Employee Created SuccessFully")
                    .data(user)
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (UserAlreadyExistException ex){
            ApiResponse<?>  error = ApiResponse.builder()
                    .success(false)
                    .data(null)
                    .message(ex.getMessage())
                    .code("USER_ALREADY_EXIST")

                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

        } catch (Exception ex){
            ApiResponse<?> error = ApiResponse.builder()
                    .success(false)
                    .message(ex.getMessage())
                    .code("INTERNAL_SERVER_ERROR")
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

//    http://localhost:8080/api/v1/employee?id=1
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getEmployeeByEmail(@RequestParam Long id){
             try {
                 EmployeeResponseDto employeeResponse = employeeService.getEmployeeById(id);
                 ApiResponse<EmployeeResponseDto> apiResponse = ApiResponse.<EmployeeResponseDto>builder()
                         .data(employeeResponse)
                         .message("user found successfully")
                         .success(true)
                         .build();
                 return ResponseEntity.ok(apiResponse);
             } catch (UserNotFoundException ex){
                 ApiResponse<?> apiResponse = ApiResponse.builder()
                         .success(false)
                         .data(null)
                         .code("EMPLOYEE_NOT_FOUND")
                         .message(ex.getMessage())
                         .build();
                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
             } catch (Exception ex){
                 ApiResponse<?> apiResponse = ApiResponse.builder()
                         .success(false)
                         .message(ex.getMessage())
                         .code("INTERNAL_SERVER_ERROR")
                         .build();
                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
             }
    }


//    http://localhost:8080/api/v1/employee?pageNo=1&pageSize=2&sortBy=name&sortDir=asc
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllEmployee(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "4", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        try {
            List<EmployeeResponseDto> allEmployee = employeeService.getAllEmployee(pageNo, pageSize, sortBy, sortDir);
            ApiResponse<List<EmployeeResponseDto>> apiResponse = ApiResponse.<List<EmployeeResponseDto>>builder()
                    .success(true)
                    .message("find all employee successfully")
                    .data(allEmployee)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<?> apiResponse = ApiResponse.builder()
                    .success(false)
                    .code("INTERNAL_SERVER_ERROR")
                    .data(null)
                    .message("something went wrong")
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

//    http://localhost:8080/api/v1/employee/delete?id=2
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<?>> deleteById(@RequestParam Long id){
        try {
            boolean b = employeeService.deleteById(id);
            ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                    .success(true)
                    .message("deleted successfully")
                    .data("Deleted Successfully")
                    .code("DELETED_SUCCESSFULLY")
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception ex){
            ApiResponse<?> apiResponse = ApiResponse.builder()
                    .success(false)
                    .code("NO_EMPLOYEE_EXIST")
                    .message(ex.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

//    http://localhost:8080/api/v1/employee/update?id=1
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<?>> updateById(@RequestParam Long id, @Valid @RequestBody Map<String, Object> updates){
        try {
            EmployeeResponseDto employeeResponseDto = employeeService.updateById(id, updates);
            ApiResponse<EmployeeResponseDto> apiResponse = ApiResponse.<EmployeeResponseDto>builder()
                    .success(true)
                    .message("updated successfully")
                    .data(employeeResponseDto)
                    .code("EMPLOYEE_UPDATED_SUCCESSFULLY")
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (UserNotFoundException e) {
            ApiResponse<?> apiResponse = ApiResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .code("USER_NOT_FOUND")
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);

        } catch (Exception e) {
            ApiResponse<?> apiResponse = ApiResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .code("INTERNAL_SERVER_ERROR")
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }


}
