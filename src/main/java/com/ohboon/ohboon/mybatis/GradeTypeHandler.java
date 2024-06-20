package com.ohboon.ohboon.mybatis;

import com.ohboon.ohboon.dto.Grade;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(Grade.class)
public class GradeTypeHandler implements TypeHandler<Grade> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Grade parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getLabel()); // 세팅
    }

    // 값을 끄집어낼 때 2가지 방법이 있음. -> rs.getString()의 인덱스값 또는 column 명
    @Override
    public Grade getResult(ResultSet rs, String columnName) throws SQLException {
        String grade = rs.getString(columnName); // member, manager, admin
        Grade result = null;
        if(grade.equalsIgnoreCase("standby")){
            result = Grade.STANDBY;
        }else if(grade.equalsIgnoreCase("member")){
            result = Grade.MEMBER;
        }else if(grade.equalsIgnoreCase("manager")){
            result = Grade.MANAGER;
        }else if(grade.equalsIgnoreCase("admin")){
            result = Grade.ADMIN;
        }else if(grade.equalsIgnoreCase("restricted1")){
            result = Grade.RESTRICTED1;
        }else if(grade.equalsIgnoreCase("restricted2")){
            result = Grade.RESTRICTED2;
        }else if(grade.equalsIgnoreCase("banned")){
            result = Grade.BANNED;
        }else if(grade.equalsIgnoreCase("deleted")){
            result = Grade.DELETED;
        }
        return result;
    }

    @Override
    public Grade getResult(ResultSet rs, int columnIndex) throws SQLException {
        String grade = rs.getString(columnIndex); // member, manager, admin
        Grade result = null;
        if(grade.equalsIgnoreCase("standby")){
            result = Grade.STANDBY;
        }else if(grade.equalsIgnoreCase("member")){
            result = Grade.MEMBER;
        }else if(grade.equalsIgnoreCase("manager")){
            result = Grade.MANAGER;
        }else if(grade.equalsIgnoreCase("admin")){
            result = Grade.ADMIN;
        }else if(grade.equalsIgnoreCase("restricted1")){
            result = Grade.RESTRICTED1;
        }else if(grade.equalsIgnoreCase("restricted2")){
            result = Grade.RESTRICTED2;
        }else if(grade.equalsIgnoreCase("banned")){
            result = Grade.BANNED;
        }else if(grade.equalsIgnoreCase("deleted")){
            result = Grade.DELETED;
        }
        return result;
    }

    @Override
    public Grade getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String grade = cs.getString(columnIndex); // member, manager, admin
        Grade result = null;
        if(grade.equalsIgnoreCase("standby")){
            result = Grade.STANDBY;
        }else if(grade.equalsIgnoreCase("member")){
            result = Grade.MEMBER;
        }else if(grade.equalsIgnoreCase("manager")){
            result = Grade.MANAGER;
        }else if(grade.equalsIgnoreCase("admin")){
            result = Grade.ADMIN;
        }else if(grade.equalsIgnoreCase("restricted1")){
            result = Grade.RESTRICTED1;
        }else if(grade.equalsIgnoreCase("restricted2")){
            result = Grade.RESTRICTED2;
        }else if(grade.equalsIgnoreCase("banned")){
            result = Grade.BANNED;
        }else if(grade.equalsIgnoreCase("deleted")){
            result = Grade.DELETED;
        }
        return result;
    }
}
