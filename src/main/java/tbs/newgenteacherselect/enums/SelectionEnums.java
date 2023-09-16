package tbs.newgenteacherselect.enums;

public enum SelectionEnums {

    SELECTIVE("可选择", 1), UN_SELECTIVE("已关闭", 0), NO_SET("未开启", -1);
    private String name;
    private int code;

    SelectionEnums(String name, int c) {
        this.name = name;
        this.code = c;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static SelectionEnums convertTo(int cd)throws UnsupportedOperationException {

        switch (cd) {
            case 1:
                return SELECTIVE;
            case 0:
                return UN_SELECTIVE;
            case -1:
                return NO_SET;
            default:
                throw new UnsupportedOperationException("不存在其他状态");
        }
    }
}
