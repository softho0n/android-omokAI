package edu.skku.cs.omokwithmvp;

public class MenuModel implements MenuContract.MenuModel {

    private String name;
    private String easyscore;
    private String hardscore;
    private String passwd;

    public String getName() {
        return name;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getEasyscore() {
        return easyscore;
    }

    public String getHardscore() {
        return hardscore;
    }

    public MenuModel(String name, String easyscore, String hardscore, String passwd) {
        this.name = name;
        this.easyscore = easyscore;
        this.hardscore = hardscore;
        this.passwd = passwd;
    }

    @Override
    public void getIntentExtraFromView(OnApiListener listener) {
        listener.onSuccess();
    }
}
