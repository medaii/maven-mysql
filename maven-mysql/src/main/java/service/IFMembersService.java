package service;

import loko.DAO.IFMailsDAO;
import loko.DAO.IFMembersDAO;
import loko.DAO.IFPhoneDAO;
import loko.DAO.IFUser;

public interface IFMembersService extends IFMembersDAO, IFPhoneDAO, IFMailsDAO, IFUser {

}
