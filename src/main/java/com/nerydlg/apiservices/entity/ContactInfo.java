package com.nerydlg.apiservices.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class ContactInfo {
    private String name;
    private String mail;
    private String phone;
    private String message;
    private String to;

}

