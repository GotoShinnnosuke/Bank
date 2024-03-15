package jp.ac.ohara.oharabank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.ac.ohara.oharabank.model.BankAccount;
import jp.ac.ohara.oharabank.repository.BankAccountRepository;

@Service
public class LoginFormService implements UserDetailsService{
	@Autowired
	private BankAccountRepository repository;

	@Override
	public UserDetails loadUserByUsername(String userName)throws UsernameNotFoundException {
		System.out.println("serach name : " +userName);
	 	BankAccount user = this.repository.findByUserNameEquals(userName); // emailで検索するので「EmailEquals」としている
		System.out.println(user.toString());
		return user;
	}
}