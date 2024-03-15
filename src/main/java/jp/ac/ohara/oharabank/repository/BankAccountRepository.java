package jp.ac.ohara.oharabank.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.ac.ohara.oharabank.model.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{

	BankAccount findByUserNameEquals(String userName);
}
