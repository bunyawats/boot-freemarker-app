package com.ssc.freemarker.FreeMarkerApp.repository.util;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import org.jooq.*;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;

// We're binding <T> = Object (unknown database type), and <U> = JsonElement (user type)
public class MySqlJSONGsonBinding implements Binding<JSON, JsonElement> {

    // The converter does all the work
    @Override
    public Converter<JSON, JsonElement> converter() {
        return new Converter<JSON, JsonElement> ( ) {
            @Override
            public JsonElement from(JSON t) {
                return t == null ? JsonNull.INSTANCE : new Gson ( ).fromJson ( "" + t, JsonElement.class );
            }

            @Override
            public JSON to(JsonElement u) {
                return u == null || u == JsonNull.INSTANCE ? null : JSON.valueOf ( new Gson ( ).toJson ( u ) );
            }

            @Override
            public Class<JSON> fromType() {
                return JSON.class;
            }

            @Override
            public Class<JsonElement> toType() {
                return JsonElement.class;
            }
        };
    }

    @Override
    public void sql(BindingSQLContext<JsonElement> bindingSQLContext) throws SQLException {
        bindingSQLContext.render ( ).sql ( "?" );
    }

    // Registering VARCHAR types for JDBC CallableStatement OUT parameters
    @Override
    public void register(BindingRegisterContext<JsonElement> ctx) throws SQLException {
        ctx.statement ( ).registerOutParameter ( ctx.index ( ), Types.VARCHAR );
    }

    // Converting the JsonElement to a String value and setting that on a JDBC PreparedStatement
    @Override
    public void set(BindingSetStatementContext<JsonElement> ctx) throws SQLException {
        ctx.statement ( ).setString ( ctx.index ( ), Objects.toString ( ctx.convert ( converter ( ) ).value ( ), null ) );
    }

    // Getting a String value from a JDBC ResultSet and converting that to a JsonElement
    @Override
    public void get(BindingGetResultSetContext<JsonElement> ctx) throws SQLException {
        ctx.convert ( converter ( ) ).value ( JSON.valueOf ( ctx.resultSet ( ).getString ( ctx.index ( ) ) ) );
    }

    // Getting a String value from a JDBC CallableStatement and converting that to a JsonElement
    @Override
    public void get(BindingGetStatementContext<JsonElement> ctx) throws SQLException {
        ctx.convert ( converter ( ) ).value ( JSON.valueOf ( ctx.statement ( ).getString ( ctx.index ( ) ) ) );
    }

    // Setting a value on a JDBC SQLOutput (useful for Oracle OBJECT types)
    @Override
    public void set(BindingSetSQLOutputContext<JsonElement> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException ( );
    }

    // Getting a value from a JDBC SQLInput (useful for Oracle OBJECT types)
    @Override
    public void get(BindingGetSQLInputContext<JsonElement> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException ( );
    }
}