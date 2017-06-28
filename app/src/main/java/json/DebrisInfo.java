package json;

import java.util.List;

/**
 * Created by xuhongliang on 2017/6/21.
 */
public class DebrisInfo {

    private List<Debris> debrisList;

    //碎片说明
    private String debrisHelp;


    public List<Debris> getDebrisList() {
        return debrisList;
    }

    public void setDebrisList(List<Debris> debrisList) {
        this.debrisList = debrisList;
    }

    public String getDebrisHelp() {
        return debrisHelp;
    }

    public void setDebrisHelp(String debrisHelp) {
        this.debrisHelp = debrisHelp;
    }
}
