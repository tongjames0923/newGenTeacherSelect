package tbs.newgenteacherselect.listenners;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import tbs.newgenteacherselect.model.TeacherRegisterVO;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class TeacherExcelListenner implements ReadListener<TeacherRegisterVO> {


    private List<TeacherRegisterVO> list = new LinkedList<>();

    private Consumer<List<TeacherRegisterVO>> consumer;


    public TeacherExcelListenner(Consumer<List<TeacherRegisterVO>> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void invoke(TeacherRegisterVO teacherRegisterVO, AnalysisContext analysisContext) {
        list.add(teacherRegisterVO);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        consumer.accept(list);
    }
}
