package com.demo.dto;

import com.demo.entity.Account;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class LoginDto {
	private String path;
	private String error;
	private Account account;
	private String token;
}
