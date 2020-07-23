package com.cdio.petshop.model;

import lombok.Data;
@Data
public class UserTemp {
    private String presentPassword;
    private String newPassword;
    private String newPasswordConfirm;
}
