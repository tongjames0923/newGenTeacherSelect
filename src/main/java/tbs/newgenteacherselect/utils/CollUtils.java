package tbs.newgenteacherselect.utils;

import cn.hutool.core.collection.CollUtil;

import java.util.List;

public class CollUtils {
    public static <T> T topOrDefault(List<T> ls, T def) {
        if (CollUtil.isEmpty(ls))
            return def;
        return ls.get(0);
    }
}
