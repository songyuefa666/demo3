package com.sss.demo3.dao;

import com.sss.demo3.entity.SysParam;
import com.sss.demo3.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SysParamDao {

    public List<SysParam> getAll() {
        List<SysParam> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM sys_params";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                SysParam p = new SysParam();
                p.setId(rs.getInt("id"));
                p.setParamName(rs.getString("param_name"));
                p.setParamValue(rs.getString("param_value"));
                p.setDescription(rs.getString("description"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    public void updateValue(String paramName, String paramValue) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE sys_params SET param_value=? WHERE param_name=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, paramValue);
            ps.setString(2, paramName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }
}