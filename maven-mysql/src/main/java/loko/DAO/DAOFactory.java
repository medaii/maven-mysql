/**
 * 
 */
package loko.DAO;

import loko.DB.DBHibernateSqlExecutor;

/**
 * Tovarna pro vytvareni DAO implementaci
 */
public class DAOFactory {

	private DAOFactory() {
	}

	/**
	 * Vrati instanci implementace zadaneho DAO rozhrani.
	 * @param daoClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createDAO(Class<T> daoClass) {
		if(daoClass.isAssignableFrom(IFMailsDAO.class)) {
			return (T) createMailsDao();
		}
		if (daoClass.isAssignableFrom(IFPhoneDAO.class)) {
			return (T) createPhonesDao();
		}
		if(daoClass.isAssignableFrom(IFMembersDAO.class)) {
			return (T) createMembersDao();
		}
		if(daoClass.isAssignableFrom(IFUserDAO.class)) {
			return (T) createUserDao();
		}
		throw new IllegalArgumentException("Neznamy typ DAO " + daoClass.getName());
	}
	
	/**
	 * Vrati novou instanci DAO pro praci s maily.
	 * @return
	 */
	private static MailsHibernateDAOImpl createMailsDao() {
		return new MailsHibernateDAOImpl(DBHibernateSqlExecutor.getInstance());
	}
	/**
	 * Vrati novou instanci DAO pro praci s telefony.
	 * @return
	 */
	private static PhonesHibernateDAOImpl createPhonesDao() {
		return new PhonesHibernateDAOImpl(DBHibernateSqlExecutor.getInstance());
	}
	/**
	 * Vrati nobou instanci DAO pro praci s members
	 * @return
	 */
	private static MembersHibernateDAOImpl createMembersDao() {
		return new MembersHibernateDAOImpl(DBHibernateSqlExecutor.getInstance());
	}
	/**
	 * Vrati nobou instanci DAO pro praci s users
	 * @return
	 */
	private static UserHibernateDAOimpl createUserDao() {
		return new UserHibernateDAOimpl(DBHibernateSqlExecutor.getInstance());
	}
}
