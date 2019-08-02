package com.paladin.credit.service.department;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.base.Strings;
import com.paladin.common.core.ConstantsContainer;
import com.paladin.credit.model.department.DepartmentAdministrativeLicense;
import com.paladin.credit.model.department.DepartmentAdministrativePunishment;
import com.paladin.credit.model.department.DepartmentCredit;
import com.paladin.credit.model.department.DepartmentPersonCredit;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.spring.SpringContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;

/**
 * <上传数据至卫健委前置库>
 *
 * @author Huangguochen
 * @create 2019/7/30 16:43
 */
@Component
public class WJWDepartmentCreditUploadService implements SpringContainer {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(WJWDepartmentCreditUploadService.class);

    @Value("${credit.upload.jdbc.url}")
    private String url;

    @Value("${credit.upload.jdbc.username}")
    private String username;

    @Value("${credit.upload.jdbc.password}")
    private String password;

    @Value("${credit.upload.jdbc.name}")
    private String name;

    private static DruidDataSource dataSource;


    @Override
    public boolean initialize() {
        LOGGER.info("<-----------------------初始化sqlserver数据源开始------------------------->");
        dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setPassword(password);
        dataSource.setUsername(username);
        dataSource.setName(name);
        dataSource.setMaxWait(10000);
        LOGGER.info("<-----------------------初始化换sqlserver数据源结束------------------------->");
        return true;
    }

    /**获取数据库连接*/
    private static Connection getConn() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            if(conn == null)
                throw new SQLException("无法获取到Connection，是否数据库已经启动或者配置错误！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**执行查询语句方法*/
    private RS executeQuery(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = WJWDepartmentCreditUploadService.getConn();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                if (params[i] != null) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            ResultSet rs = ps.executeQuery();
            return new RS(conn, ps, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**执行更新语句方法*/
    private   int executeUpdate(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = WJWDepartmentCreditUploadService.getConn();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps);
        }
        return 0;
    }


    /**释放连接资源*/
    public static void close(ResultSet rs) {
        close(null, null, rs);
    }

    /**释放连接资源*/
    public static void close(Connection conn) {
        close(conn, null, null);
    }

    /**释放连接资源*/
    public static void close(Connection conn, Statement st) {
        close(conn, st, null);
    }

    /**释放连接资源*/
    public static void close(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




    public static class RS {
        private Connection conn = null;
        private Statement st = null;
        private ResultSet rs;

        public RS(Connection conn, Statement st, ResultSet rs) {
            this.conn = conn;
            this.st = st;
            this.rs = rs;
        }

        public ResultSet getRs() {
            return this.rs;
        }

        public void close() {
            WJWDepartmentCreditUploadService.close(conn, st, rs);
        }

    }

    /**查询前置库数据*/
   public String searchRemoteInfo(String suffixSql, Object... params) {
       String countPrefixSql = " select count(0) ";
       String selectPrefixSql = " select ID  ";
       RS rs = executeQuery(countPrefixSql + suffixSql, params);
       if (rs == null) {
           throw new BusinessException("统计前置库对应数据失败");
       }
       ResultSet resultSet = rs.getRs();
       int count = 0;
       String cancelId = "";
       try {
           if (resultSet.next()) {
                count = resultSet.getInt(1);
           }
       } catch (SQLException e) {
           throw new BusinessException("统计前置库对应数据出错",e.getCause());
       } finally {
           rs.close();
       }

       if (count == 0 || count > 1) {
           throw new BusinessException("该信息不在可撤销的时间范围之内或者前置库存在多条相同数据");
       }

       RS query = executeQuery(selectPrefixSql + suffixSql, params);
       if (query == null) {
           throw new BusinessException("查询前置库对应数据失败");
       }
       ResultSet set = query.getRs();
       try {
           if (set.next()) {
               cancelId = set.getString("ID");
           }
       } catch (SQLException e) {
           throw new BusinessException("查询前置库对应数据出错",e.getCause());
       } finally {
           query.close();
       }

     return cancelId;
   }

    /**删除前置库数据*/
    public int deleteRemoteInfoById(String id, String tableName) {
        if (Strings.isNullOrEmpty(id)) {
            throw new BusinessException("无法获取到前置库对应的信息");
        }
        String deleteSql = " DELETE FROM dbo." + tableName + " WHERE ID = ? ";
        int i = executeUpdate(deleteSql, id);
        if (i == 0) {
            throw new BusinessException("删除前置库对应信息出错");
        }
        return i ;
    }


    /**执行法人行政处罚插入*/
    public  int executeOrgPunishmentInsert(DepartmentAdministrativePunishment punishment) {
        String sql = "INSERT INTO DBO.frxzcf(CF_XDR_MC, CF_XDR_SHXYM, CF_XDR_GSDJ, CF_XDR_ZDM, CF_XDR_SWDJ, CF_FR, CF_SFZ, CF_AJMC, CF_WSH, CF_BM, CF_CFLB1, CF_CFLB2, CF_SY, CF_YJ, CF_JG, CF_JDRQ, CF_XZJG, CF_GSJZQ, BZ, CF_SYFW) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = WJWDepartmentCreditUploadService.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1,punishment.getName());
            ps.setString(2,punishment.getSocialCreditCode());
            ps.setString(3,punishment.getCommercialRegistrationNumber());
            ps.setString(4,punishment.getOrganizationCode());
            ps.setString(5,punishment.getTaxRegistrationNumber());
            ps.setString(6,punishment.getChargePerson());
            ps.setString(7,punishment.getIdentificationNo());
            ps.setString(8,punishment.getPunishmentName());
            ps.setString(9,punishment.getPunishmentDocumentNumber());
            ps.setString(10,punishment.getPunishmentPowerCode());
            ps.setString(11,punishment.getPunishmentTypeOne() == null ? null: ConstantsContainer.getTypeValue("punishment-type",punishment.getPunishmentTypeOne().toString()));
            ps.setString(12,punishment.getPunishmentTypeTwo() == null ? null: ConstantsContainer.getTypeValue("punishment-type",punishment.getPunishmentTypeTwo().toString()));
            ps.setString(13,punishment.getPunishmentCause());
            ps.setString(14,punishment.getPunishmentBasis());
            ps.setString(15,punishment.getPunishmentResult());
            ps.setDate(16,punishment.getPunishmentDecisionTime() == null ? null: new Date(punishment.getPunishmentDecisionTime().getTime()));
            ps.setString(17,punishment.getPunishmentOrganization());
            ps.setDate(18,punishment.getPunishmentEffectivityEndTime() == null ? null: new Date(punishment.getPunishmentEffectivityEndTime().getTime()));
            ps.setString(19,punishment.getRemark());
            ps.setInt(20,punishment.getInformationUsageScope());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps);
        }
        return 0;
    }

    /**执行自然人行政处罚插入*/
    public  int executePeoplePunishmentInsert(DepartmentAdministrativePunishment punishment) {
        String sql = "INSERT INTO DBO.zrrxzcf(CF_XDR_MC, CF_SFZ, CF_AJMC, CF_WSH, CF_BM, CF_CFLB1, CF_CFLB2, CF_SY, CF_YJ, CF_JG, CF_JDRQ, CF_XZJG, CF_GSJZQ, BZ, CF_SYFW) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = WJWDepartmentCreditUploadService.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1,punishment.getName());
            ps.setString(2,punishment.getIdentificationNo());
            ps.setString(3,punishment.getPunishmentName());
            ps.setString(4,punishment.getPunishmentDocumentNumber());
            ps.setString(5,punishment.getPunishmentPowerCode());
            ps.setString(6,punishment.getPunishmentTypeOne() == null ? null: ConstantsContainer.getTypeValue("punishment-type",punishment.getPunishmentTypeOne().toString()));
            ps.setString(7,punishment.getPunishmentTypeTwo() == null ? null: ConstantsContainer.getTypeValue("punishment-type",punishment.getPunishmentTypeTwo().toString()));
            ps.setString(8,punishment.getPunishmentCause());
            ps.setString(9,punishment.getPunishmentBasis());
            ps.setString(10,punishment.getPunishmentResult());
            ps.setDate(11,punishment.getPunishmentDecisionTime() == null ? null: new Date(punishment.getPunishmentDecisionTime().getTime()));
            ps.setString(12,punishment.getPunishmentOrganization());
            ps.setDate(13,punishment.getPunishmentEffectivityEndTime() == null ? null: new Date(punishment.getPunishmentEffectivityEndTime().getTime()));
            ps.setString(14,punishment.getRemark());
            ps.setInt(15,punishment.getInformationUsageScope());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps);
        }
        return 0;
    }

    /**执行法人行政许可插入*/
    public int executeOrgLicenseInsert(DepartmentAdministrativeLicense license) {
        String sql = "INSERT INTO DBO.frxzxk(XK_XDR_SHXYM, XK_XDR_GSDJ, XK_XDR_ZDM, XK_XDR_SWDJ, XK_XDR, XK_XMMC, XK_WSH, XK_NR, XK_SPLB, XK_BM, XK_XZJG, XK_JDRQ, XK_JZQ, XK_SYFW, XK_FR, XK_SFZ, BZ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = WJWDepartmentCreditUploadService.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1,license.getSocialCreditCode());
            ps.setString(2,license.getCommercialRegistrationNumber());
            ps.setString(3,license.getOrganizationCode());
            ps.setString(4,license.getTaxRegistrationNumber());
            ps.setString(5,license.getName());
            ps.setString(6,license.getLicenseName());
            ps.setString(7,license.getLicenseDocumentNumber());
            ps.setString(8,license.getLicenseText());
            ps.setString(9,license.getExamineType());
            ps.setString(10,license.getLicenseAuthorityCode());
            ps.setString(11,license.getLicenseOrganization());
            ps.setDate(12,license.getLicenseDecisionTime() == null ? null : new Date(license.getLicenseDecisionTime().getTime()) );
            ps.setDate(13,license.getLicenseEffectivityEndTime() == null ? null : new Date(license.getLicenseEffectivityEndTime().getTime()) );
            ps.setInt(14, license.getInformationUsageScope());
            ps.setString(15, license.getChargePerson());
            ps.setString(16, license.getIdentificationNo());
            ps.setString(17, license.getRemark());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps);
        }
        return 0;
    }

    /**执行自然人行政许可插入*/
    public int executePeopleLicenseInsert(DepartmentAdministrativeLicense license) {
        String sql = "INSERT INTO DBO.zrrxzxk(XK_XDR, XK_XDR_SFZ, XK_XMMC, XK_WSH, XK_NR, XK_SPLB, XK_BM, XK_XZJG, XK_JDRQ, XK_JZQ, XK_SYFW, BZ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = WJWDepartmentCreditUploadService.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1,license.getName());
            ps.setString(2, license.getIdentificationNo());
            ps.setString(3,license.getLicenseName());
            ps.setString(4,license.getLicenseDocumentNumber());
            ps.setString(5,license.getLicenseText());
            ps.setString(6,license.getExamineType());
            ps.setString(7,license.getLicenseAuthorityCode());
            ps.setString(8,license.getLicenseOrganization());
            ps.setDate(9,license.getLicenseDecisionTime() == null ? null : new Date(license.getLicenseDecisionTime().getTime()) );
            ps.setDate(10,license.getLicenseEffectivityEndTime() == null ? null : new Date(license.getLicenseEffectivityEndTime().getTime()) );
            ps.setInt(11, license.getInformationUsageScope());
            ps.setString(12, license.getRemark());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps);
        }
        return 0;
    }

    /**执行法人红榜插入*/
    public int executeOrgRedInsert(DepartmentCredit departmentCredit) {
        String sql = "INSERT INTO DBO.frred(TYSHXYDM, GSZCH, ZZJGDM, BZ, RDRQ, RDBMQC, RDWH, JGQC, RYMC, RYNR, RYDJ) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = WJWDepartmentCreditUploadService.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1,departmentCredit.getSocialCreditCode());
            ps.setString(2,departmentCredit.getCommercialRegistrationNumber());
            ps.setString(3,departmentCredit.getOrganizationCode());
            ps.setString(4,departmentCredit.getRemark());
            ps.setDate(5,departmentCredit.getAffirmTime() == null ? null : new Date(departmentCredit.getAffirmTime().getTime()));
            ps.setString(6,departmentCredit.getAffirmDepartmentName());
            ps.setString(7,departmentCredit.getAffirmWrit());
            ps.setString(8,departmentCredit.getName());
            ps.setString(9,departmentCredit.getHonorName());
            ps.setString(10,departmentCredit.getHonorText());
            ps.setString(11,departmentCredit.getHonorLevel() == null ? null : ConstantsContainer.getTypeValue("honor-level-type",departmentCredit.getHonorLevel().toString()));
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps);
        }
        return 0;
    }


    /**执行法人黑榜插入*/
    public int executeOrgBlackInsert(DepartmentCredit departmentCredit) {
        String sql = "INSERT INTO DBO.frblack(TYSHXYDM, GSZCH, ZZJGDM, FDDBR, RDWH, RDDW, JGQC, ZYSXSS, ZCDZ, QRYZSXSJ, XZCFHPJNR) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = WJWDepartmentCreditUploadService.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1,departmentCredit.getSocialCreditCode());
            ps.setString(2,departmentCredit.getCommercialRegistrationNumber());
            ps.setString(3,departmentCredit.getOrganizationCode());
            ps.setString(4,departmentCredit.getChargePerson());
            ps.setString(5,departmentCredit.getAffirmWrit());
            ps.setString(6,departmentCredit.getAffirmDepartmentName());
            ps.setString(7,departmentCredit.getName());
            ps.setString(8,departmentCredit.getLosePromiseTruth());
            ps.setString(9,departmentCredit.getRegisteredAddress());
            ps.setDate(10,departmentCredit.getLosePromiseTime() == null ? null : new Date(departmentCredit.getLosePromiseTime().getTime()));
            ps.setString(11,departmentCredit.getPunishText());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps);
        }
        return 0;
    }

    /**执行行业评定插入*/
    public int executeHypdInsert(DepartmentCredit departmentCredit) {
        String sql = "INSERT INTO DBO.hypd(TYSHXYDM, GSZCH, ZZJGDM, PDJG, PDRQ, PDMC, JGQC, PDJGMC, BZ) VALUES (?,?,?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = WJWDepartmentCreditUploadService.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1,departmentCredit.getSocialCreditCode());
            ps.setString(2,departmentCredit.getCommercialRegistrationNumber());
            ps.setString(3,departmentCredit.getOrganizationCode());
            ps.setString(4,departmentCredit.getAssessmentResult() == null ? null : ConstantsContainer.getTypeValue("assessment-result-type",departmentCredit.getAssessmentResult().toString()));
            ps.setDate(5,departmentCredit.getAffirmTime() == null ? null : new Date(departmentCredit.getAffirmTime().getTime()));
            ps.setString(6,departmentCredit.getAssessmentResultName());
            ps.setString(7,departmentCredit.getName());
            ps.setString(8,departmentCredit.getAffirmDepartmentName());
            ps.setString(9,departmentCredit.getRemark());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps);
        }
        return 0;
    }


    /**执行自然人红榜插入*/
    public int executePeopleRedInsert(DepartmentPersonCredit departmentPersonCredit) {
        String sql = "INSERT INTO DBO.zrrred(XM, SFZH, BZ, RDRQ, RDBMQC, RDWH, RYMC, RYNR, RYDJ) VALUES (?,?,?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = WJWDepartmentCreditUploadService.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1,departmentPersonCredit.getName());
            ps.setString(2,departmentPersonCredit.getIdentificationNo());
            ps.setString(3,departmentPersonCredit.getRemark());
            ps.setDate(4,departmentPersonCredit.getAffirmTime() == null ? null : new Date(departmentPersonCredit.getAffirmTime().getTime()));
            ps.setString(5,departmentPersonCredit.getAffirmDepartmentName());
            ps.setString(6,departmentPersonCredit.getAffirmWrit());
            ps.setString(7,departmentPersonCredit.getHonorName());
            ps.setString(8,departmentPersonCredit.getHonorText());
            ps.setString(9,departmentPersonCredit.getHonorLevel() == null ? null : ConstantsContainer.getTypeValue("honor-level-type",departmentPersonCredit.getHonorLevel().toString()));
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps);
        }
        return 0;
    }


    /**执行自然人黑榜插入*/
    public int executePeopleBlackInsert(DepartmentPersonCredit departmentPersonCredit) {
        String sql = "INSERT INTO DBO.zrrblack(FZRXM, SFZH, RDWH, RDDW, ZYSXSS, QRYZSXSJ, XZCFHPJNR) VALUES (?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = WJWDepartmentCreditUploadService.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1,departmentPersonCredit.getChargePersonName());
            ps.setString(2,departmentPersonCredit.getIdentificationNo());
            ps.setString(3,departmentPersonCredit.getAffirmWrit());
            ps.setString(4,departmentPersonCredit.getAffirmDepartmentName());
            ps.setString(5,departmentPersonCredit.getLosePromiseTruth());
            ps.setDate(6,departmentPersonCredit.getLosePromiseTime() == null ? null : new Date(departmentPersonCredit.getLosePromiseTime().getTime()));
            ps.setString(7,departmentPersonCredit.getPunishText());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps);
        }
        return 0;
    }

}
