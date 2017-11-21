package loko.DB;

import java.sql.Connection;
/**
 * 
 * @author Erik Markoviè
 *
 */
public interface IFDBConectionManager {
	/**
	 * vrácení DB pøipojení  
	 * @return
	 */
	Connection getConnection();
}
