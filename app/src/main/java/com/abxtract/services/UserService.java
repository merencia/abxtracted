package com.abxtract.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abxtract.models.User;
import com.abxtract.repositories.TenantRepository;
import com.abxtract.repositories.UserRepository;
import com.abxtract.services.google.GoogleUserDTO;

@Service
public class UserService {
	@Autowired
	private UserRepository users;
	@Autowired
	private TenantRepository tenants;

	public User save(GoogleUserDTO dto) {

	}
//
//	public User find(OAuth2Authentication auth) {
//		final String id = ((Map<String, Object>) auth.getUserAuthentication().getDetails())
//				.get( "sub" )
//				.toString();
//		return users.findByGoogleId( id );
//	}
//
//	public User process(final OAuth2Authentication auth) {
//		final User data = fromData( auth );
//		final User user = users.findByGoogleId( data.getGoogleId() );
//		if (user != null) {
//			user.setEmail( data.getEmail() );
//			user.setName( data.getName() );
//			user.setEmailVerified( data.isEmailVerified() );
//			user.setPicture( data.getPicture() );
//			user.setToken( data.getToken() );
//			return users.save( user );
//		}
//		data.setTenant( tenants.save( new Tenant() ) );
//		return users.save( data );
//	}
//
//	private User fromData(final OAuth2Authentication auth) {
//		final Map<String, Object> dto = (Map<String, Object>) auth.getUserAuthentication().getDetails();
//		return User.builder()
//				.email( dto.get( "email" ).toString() )
//				.emailVerified( (Boolean) dto.get( "email_verified" ) )
//				.name( dto.get( "name" ).toString() )
//				.picture( dto.get( "picture" ).toString() )
//				.googleId( dto.get( "sub" ).toString() )
//				.token( ((OAuth2AuthenticationDetails) auth.getDetails()).getTokenValue() )
//				.build();
//	}
}
