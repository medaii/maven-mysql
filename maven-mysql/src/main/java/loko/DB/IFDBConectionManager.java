package loko.DB;

import java.sql.Connection;

public interface IFDBConectionManager {
	/**
	 * vrácení DB pøipojení  
	 * @return
	 */
	Connection getConnection();
}
