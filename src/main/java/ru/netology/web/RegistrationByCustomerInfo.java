package ru.netology.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationByCustomerInfo {
    private String fullName;
    private String phoneNumber;
}
