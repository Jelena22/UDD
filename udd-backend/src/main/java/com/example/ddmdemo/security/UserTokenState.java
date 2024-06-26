package com.example.ddmdemo.security;

import com.example.ddmdemo.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenState {

	private String accessToken;
	private int expiresIn;
	private User user;

}
