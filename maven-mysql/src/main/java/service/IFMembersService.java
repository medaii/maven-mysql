package service;

import loko.DAO.IFMailsDAO;
import loko.DAO.IFMembersDAO;
import loko.DAO.IFPhoneDAO;
import loko.DAO.IFUserDAO;

public interface IFMembersService extends IFMembersDAO, IFPhoneDAO, IFMailsDAO, IFUserDAO {

}
