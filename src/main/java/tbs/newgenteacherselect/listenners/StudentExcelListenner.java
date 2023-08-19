package tbs.newgenteacherselect.listenners;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import tbs.newgenteacherselect.model.StudentRegisterVO;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class StudentExcelListenner implements ReadListener<StudentRegisterVO> {


    private List<StudentRegisterVO> list = new LinkedList<>();

    private Consumer<List<StudentRegisterVO>> consumer;


    public StudentExcelListenner(Consumer<List<StudentRegisterVO>> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void invoke(StudentRegisterVO StudentRegisterVO, AnalysisContext analysisContext) {
        list.add(StudentRegisterVO);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        consumer.accept(list);
    }
}
