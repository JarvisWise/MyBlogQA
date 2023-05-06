package org.simple.blog.dao.connection;

import org.apache.log4j.Logger;
import org.simple.blog.tools.AppProperties;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Hashtable;

public class Oracle implements ConnectionInterface{

    protected Connection connection = null;
    protected PreparedStatement statement = null;
    protected ResultSet result = null;
    private DataSource dataSource;
    private Context context;
    private Hashtable<String, String> hashtable = new Hashtable<>();
    private static final Logger logger = Logger.getLogger(Oracle.class);

    @Override
    public void connect() {
        try {
            if (!AppProperties.isRead())
                AppProperties.readProperties();

            hashtable.put(Context.INITIAL_CONTEXT_FACTORY, AppProperties.getDbContextFactory());
            hashtable.put(Context.PROVIDER_URL, AppProperties.getDbProviderUrl());
            context = new InitialContext(hashtable);
            dataSource = (DataSource) context.lookup(AppProperties.getDbDataSourceName());
            connection = dataSource.getConnection();
        } catch (SQLException | NamingException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public void disconnect() {
        try {
            if(connection!=null){
                connection.close();
            }
            if(context!=null){
                context.close();
            }
        } catch (SQLException | NamingException e) {
            logger.warn(e.getMessage());
        }
    }
}
