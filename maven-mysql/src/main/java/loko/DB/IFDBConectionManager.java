package loko.DB;

import java.sql.Connection;

public interface IFDBConectionManager {
	/**
	 * vr�cen� DB p�ipojen�  
	 * @return
	 */
	Connection getConnection();
}
