package tbs.newgenteacherselect.dao;

import org.springframework.util.StringUtils;
import tbs.newgenteacherselect.dao.QO.TeacherQO;
import tbs.pojo.dto.TeacherDetail;
import tbs.utils.sql.impl.SQL_QueryImpl;
import tbs.framework.sql.query.Page;
import tbs.framework.sql.query.Sortable;

import java.util.LinkedList;
import java.util.List;

public class TeacherQuery {

    public static final String SELECT="select";
    private String append(String now)
    {
        if("".equals(now))
            return " WHERE ";
        return now;
    }
   public   String select(TeacherQO qo, Page page, Sortable... sortable)
    {
        String tail= SQL_QueryImpl.PageingQuery(page, sortable);
        String head="select '-1' as cnt ,";

        String select= TeacherDetail.BASIC_DATA_SQL;
        if(qo==null)
            return head+ select+tail;
        String condition="";
        List<String> conditions=new LinkedList<>();
        if (qo.getDepartment()!=null)
        {
            conditions.add("departmentId = "+qo.getDepartment());
        }
        if(!StringUtils.isEmpty(qo.getNameOrPhone()))
        {
            conditions.add(String.format("bu.name like '%s%%' or bu.phone like '%s%%'",qo.getNameOrPhone(),qo.getNameOrPhone()));
        }
        if(!StringUtils.isEmpty(qo.getPositionOrTitle()))
        {
            conditions.add(String.format("st.position like '%s%%' or st.pro_title like '%s%%'",qo.getPositionOrTitle(),qo.getPositionOrTitle()));
        }
        if(qo.getScoreLevel()!=null)
        {
            head=String.format("select (SELECT COUNT(1) FROM masterrelation mr1 WHERE mr1.masterPhone=bu.phone AND mr1.studentPhone IS NULL and mr1.scoreConfigItemId=%d) AS `cnt` ,",qo.getScoreLevel());
        }
        select=head+select;
        if(conditions.isEmpty())
            return select+tail;
        else
        {
           condition=append(condition)+" ("+ conditions.get(0)+") ";
           for(int i=1;i<conditions.size();i++)
           {
               condition+="and ("+conditions.get(i)+") ";
           }
           return select+condition+tail;
        }
    }
}
