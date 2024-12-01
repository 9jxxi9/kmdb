package com.example.moviesapi.config;

import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.Dialect;

public class SqliteDbDialect extends Dialect {
    public SqliteDbDialect() {
        super(DatabaseVersion.make(3));
    }

}