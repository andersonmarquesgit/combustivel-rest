package com.combustivel.api.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.combustivel.api.entity.User;
import com.combustivel.api.security.jwt.JwtAuthenticationRequest;
import com.combustivel.api.security.jwt.JwtTokenUtil;
import com.combustivel.api.security.model.CurrentUser;
import com.combustivel.api.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@RestController
@CrossOrigin(origins = "*")//Permitindo o acesso de qualquer IP, porta, etc.
@Api(value = "Authentication")
public class AuthenticationRestController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/auth")
	@ApiOperation(value = "Autenticação de usuário", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(@ApiResponse(code = 200, message = "Success", response = CurrentUser.class, 
		responseHeaders = @ResponseHeader(name = "Location", description = "token", response = String.class)))
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
		
		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						authenticationRequest.getEmail(),
						authenticationRequest.getPassword()
				)
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		final User user = userService.findByEmail(authenticationRequest.getEmail());
		user.setPassword(null);
		return ResponseEntity.ok(new CurrentUser(token, user));
		
	}
}
