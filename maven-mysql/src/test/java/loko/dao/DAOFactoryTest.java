package loko.dao;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import loko.dao.DAOFactory;
import loko.dao.IFMailsDAO;
import loko.dao.jdbc.impl.MailsDAOImpl;

/**
 * JUNIT testy DAOFactory.
 *
 */
public class DAOFactoryTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testCreateMailDAO() {
		IFMailsDAO dao = DAOFactory.createDAO(IFMailsDAO.class);
		assertThat("Nepodarilo se ziskat implementaci MailsDAOImpl", dao, IsInstanceOf.instanceOf(MailsDAOImpl.class) );
	}

	/**
	 * Test pro vytvoreni neexistujiciho DAO.
	 */
	@Test
	public void testCreateNotExistedDAO() {
		thrown.expect(IllegalArgumentException.class);
		DAOFactory.createDAO(NotExistedDao.class);
	}

	private interface NotExistedDao {

	}
}
