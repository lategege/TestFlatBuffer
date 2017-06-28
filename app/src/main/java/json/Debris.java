package json;

/**
 * Created by xuhongliang on 2017/6/21.
 */
public class Debris {

    //碎片图片
    private String image;
    //需要兑换的数量
    private int changeNum;
    //碎片id
    private long id;
    //碎片名称
    private String name;
    //是否需要高亮
    private boolean highlight;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(int changeNum) {
        this.changeNum = changeNum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }
}
