package tbs.newgenteacherselect;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tbs.framework.dao.excel.BaseExcelDao;
import tbs.framework.excel.TestData;
import tbs.framework.excel.base.adapter.DbAdapter;
import tbs.framework.excel.base.constants.BeanConstants;
import tbs.framework.excel.base.interfaces.ICellData2BeanConverter;
import tbs.framework.excel.base.interfaces.IExcelService;
import tbs.framework.excel.base.model.CellData;
import tbs.framework.excel.base.model.CellDataItem;
import tbs.framework.excel.utils.DataUtils;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class NewGenTeacherSelectApplicationTests {
    @Resource
    BaseExcelDao baseExcelDao;

    @Resource
    IExcelService converter;


    @Resource(name = BeanConstants.EASY_EXCEL_DB_CONVERTER)
    ICellData2BeanConverter cellData2BeanConverter;

    @Test
    void contextLoads() throws Exception {
        DbAdapter adapter = new DbAdapter(baseExcelDao, TestData.class);
        List<CellDataItem> cells = converter.readFromFile(Files.newInputStream(Paths.get("C:\\Users\\tongj\\Desktop\\学生.xlsx")), adapter, 0);
        Map<Integer, CellData> datas = DataUtils.toCellData(cells);
        List<TestData> dataList = new LinkedList<>();
        for (CellData data : datas.values()) {
            dataList.add(cellData2BeanConverter.convert(data, TestData.class));
        }
        System.out.println("read done");
        Map<String, Integer> cp = new HashMap<>();
        cp.put("入学成绩", 200);
        List<CellData> celldatas = cellData2BeanConverter.convertToData(dataList, TestData.class);
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\tongj\\Desktop\\test.xlsx");
        converter.output(outputStream, DataUtils.toCellDataItemList(celldatas), cp);
        System.out.println("writeDone");
    }

}
