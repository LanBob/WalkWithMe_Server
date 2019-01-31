package com.mapper;

import java.util.List;

import com.domain.PersonDao;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDaoMapper {
	/**
	 * ���Ӳ���
	 * 
	 * @param dao
	 */
	void insert(PersonDao dao);

	/**
	 * ɾ��
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * ��
	 * 
	 * @param dao
	 */
	void update(PersonDao dao);

	/**
	 * ������ѯ����
	 * 
	 * @param id
	 * @return
	 */
	PersonDao get(Long id);

	/**
	 * ��ѯ��Ӵ����
	 * 
	 * @return
	 */
	List<PersonDao> listAll();

}
