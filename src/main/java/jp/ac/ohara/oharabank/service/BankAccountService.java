
package jp.ac.ohara.oharabank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jp.ac.ohara.oharabank.model.BankAccount;
import jp.ac.ohara.oharabank.repository.BankAccountRepository;

@Service
@Transactional

public class BankAccountService {
	@Autowired
	private BankAccountRepository repository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	/**
	 * アドレス帳一覧の取得
	 * @return List<User>
	 */
	public List<BankAccount> getAddressList() {
		List<BankAccount> entityList = this.repository.findAll();
		return entityList;
	}

	/**
	 * 詳細データの取得
	 * @param @NonNull Long index
	 * @return  AddressBook
	 */
	public BankAccount get(@NonNull Long index) {
		BankAccount addressBook = this.repository.findById(index).orElse(new BankAccount());
		return addressBook;
	}

	/**
	 * アドレス帳データの保存
	 * @param BankAccount user
	 */
	public void save(@NonNull BankAccount bankaccount) {
		bankaccount.setPassword(this.passwordEncoder.encode(bankaccount.getPassword()));
		this.repository.save(bankaccount);
	}

	/**
	 * アドレス帳データの削除
	 * @param @NonNull Long index
	 */
	public void delete(@NonNull Long index) {
		this.repository.deleteById(index);
		}
	}

