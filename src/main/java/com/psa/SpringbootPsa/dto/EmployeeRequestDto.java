package com.psa.SpringbootPsa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDto {


    @NotBlank(message = "name is required")
    @Size(min = 3, message = "name should be at least three character")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "Invalid Email Format")
    private String email;

    @NotBlank(message = "Mobile Number is required")
    @Size(min = 10, max = 10, message = "phone number size should be 10")
    private String mobile;


}
