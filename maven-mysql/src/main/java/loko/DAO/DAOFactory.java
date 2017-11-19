/**
 * 
 */
package loko.DAO;

import loko.DB.DBSqlExecutor;

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
		throw new IllegalArgumentException("Neznamy typ DAO " + daoClass.getName());
	}
	
	/**
	 * Vrati novou instanci DAO pro praci s maily.
	 * @return
	 */
	private static MailsDAOImpl createMailsDao() {
		return new MailsDAOImpl(DBSqlExecutor.getInstance());
	}
	/**
	 * Vrati novou instanci DAO pro praci s telefony.
	 * @return
	 */
	private static PhonesDAOImpl createPhonesDao() {
		return new PhonesDAOImpl(DBSqlExecutor.getInstance());
	}
}
