package com.test.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.test.domain.Contact;

@Component
@Transactional
public class ContactDao {
	@PersistenceContext
	private EntityManager entityManager;

	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	public void save(Contact contest) {

		this.getSession().save(contest);

	}

	public List<Contact> findAll() {
		DetachedCriteria dc = DetachedCriteria.forClass(Contact.class);
		Criteria criteria = dc.getExecutableCriteria(getSession());
		List<Contact> list = criteria.list();
		return list;
	}

	public Contact findById(String id) {
		Contact contact = (Contact) getSession().get(Contact.class, id);
		return contact;
	}

	public void delete(Contact contact) {
		this.getSession().delete(contact);
	}

	public void update(Contact contact) {
		this.getSession().merge(contact);
	}
}