package com.example.customerapi.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CustomerPatchReq {
    private String firstName;
    private String lastName;
    @Email(message = "Email must be valid.")
    private String email;

    public CustomerPatchReq(){
        }
    public CustomerPatchReq(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
