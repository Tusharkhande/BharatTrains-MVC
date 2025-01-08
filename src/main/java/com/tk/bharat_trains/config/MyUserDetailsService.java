package com.tk.bharat_trains.config;

//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tk.bharat_trains.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<Users> user = repository.findByUsername(username);
//		return user.map(CustomUser::new).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return repository.findByUsername(username);
	}

}
