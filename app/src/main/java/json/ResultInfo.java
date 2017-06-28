package json;

import json.DebrisInfo;

/**
 * Created by xuhongliang on 2017/6/21.
 */
public class ResultInfo {

    private int code;

    private DebrisInfo data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DebrisInfo getData() {
        return data;
    }

    public void setData(DebrisInfo data) {
        this.data = data;
    }
}
