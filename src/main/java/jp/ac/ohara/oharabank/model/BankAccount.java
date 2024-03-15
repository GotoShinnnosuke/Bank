package jp.ac.ohara.oharabank.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="bankaccount")
public class BankAccount implements UserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="account_id")
	private long accountId;
	
	@Column(name="user_name",length=255)
	private String userName;
	
	@Column(name="password")
	private String password;
	
	@Column(name="balance")
	private Integer balance;
	
	/**
	 * 権限を返す
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		return authorityList;
	}

	/**
	 * ログイン時に使用するユーザ名を返す
	 * @return メールアドレス
	 */
	@Override
	public String getUsername() {
		return this.userName; // 今回はemailにしているが、userNameでも良い
	}

	/**
	 * 有効期限のチェック
	 * @return true:有効/false:無効
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * アカウントのロック状態
	 * @return true:有効/false:無効
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * アカウントの認証情報の有効期限
	 * @return true:有効/false:無効
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}

}
