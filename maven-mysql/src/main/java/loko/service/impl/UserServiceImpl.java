package loko.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loko.dao.UserDAO;
import loko.entity.User;
import loko.service.UserService;

/**
 *
 * Servisní tøída pro DAO (obstaravající users)
 * 
 * @author Erik Markovic
 *
 *
 */
@Service
public class UserServiceImpl implements UserService {

	// instance na DAO dle entit
	@Autowired
	private UserDAO userDAO;

	// instance se žádají od factory, která posle instanci na DAO pomoci JDBC nebo
	// Hibernate

	public UserServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public void updateUser(User theUser) {
		userDAO.updateUser(theUser);
	}

	@Override
	public void changePassword(User theUser, String newPassword) {
		userDAO.changePassword(theUser, newPassword);
	}

	@Override
	public List<User> getUsers(boolean admin, int userId) {
		return userDAO.getUsers(admin, userId);
	}

	@Override
	public boolean authenticate(byte[] password, int id) {
		return userDAO.authenticate(password, id);
	}

	@Override
	public int addUser(User theUser) {
		return userDAO.addUser(theUser);
	}
}
