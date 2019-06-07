package com.combustivel.api.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.combustivel.api.entity.User;
import com.combustivel.api.security.jwt.JwtUserFactory;
import com.combustivel.api.service.UserService;

/**
 * Serviço para manipulação do {@link UserDetails}
 * @author anderson.marques
 *
 */
@Service
public class JwtUserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userService.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", email));
		} else {
			return JwtUserFactory.create(user);
		}
	}

}
