package pers.acp.core.dbconnection.instance;

import pers.acp.core.dbconnection.DBType;
import pers.acp.core.log.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zhang on 2016/7/21.
 * MySQL操作工厂
 */
public class MySQLInstance extends BaseDBInstance {

    private final LogFactory log = LogFactory.getInstance(this.getClass());

    @Override
    public ResultSet doQueryPage(DBType dbType, Connection connection, Object[] param, long currPage, long maxCount, String pKey, String[] sqlArray) throws SQLException {
        String strSQL = "select " + sqlArray[0] + " from " + sqlArray[1] +
                " inner join (select " + pKey + " as q_key_id from " + sqlArray[1] + " " + sqlArray[2] + " " + sqlArray[3] + " limit " + ((currPage - 1) * maxCount) + "," + maxCount + ") _q_tmp_t on _q_tmp_t.q_key_id=" + pKey + " " + sqlArray[2] + " " + sqlArray[3];
        log.debug("sql=" + strSQL);
        PreparedStatement pstmt = connection.prepareStatement(strSQL);
        if (param != null) {
            for (int i = 0; i < param.length * 2; i++) {
                pstmt.setObject(i + 1, param[i % param.length]);
            }
        }
        this.setPstmt(pstmt);
        return pstmt.executeQuery();
    }

}
