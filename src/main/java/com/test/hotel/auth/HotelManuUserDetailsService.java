package com.test.hotel.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class HotelManuUserDetailsService implements UserDetailsService{

	
			private final UserRepository userRepository;

			public HotelManuUserDetailsService(UserRepository userRepository) {
				super();
				this.userRepository = userRepository;
			}

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				// TODO Auto-generated method stub
				User user = userRepository.findByUsername(username);
				if(null==user) {
					throw new UsernameNotFoundException("Username not found: " +username);
				}
				return new HotelManuUserPrincipal(user);
			}
			
			
}
