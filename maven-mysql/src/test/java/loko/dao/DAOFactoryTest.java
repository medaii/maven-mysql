package loko.dao;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import loko.dao.DAOFactory;
import loko.dao.MailsDAO;
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
		MailsDAO dao = DAOFactory.createDAO(MailsDAO.class);
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
