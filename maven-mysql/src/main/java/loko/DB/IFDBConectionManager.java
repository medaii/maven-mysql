package loko.DB;

import java.sql.Connection;
/**
 * 
 * @author Erik Markovi�
 *
 */
public interface IFDBConectionManager {
	/**
	 * vr�cen� DB p�ipojen�  
	 * @return
	 */
	Connection getConnection();
}
