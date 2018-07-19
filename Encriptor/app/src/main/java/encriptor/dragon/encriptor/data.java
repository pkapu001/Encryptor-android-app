package encriptor.dragon.encriptor;

public class data {

    private String org_text , mod_text , path_text ,time ;
    private Long id;
    private boolean selected ;



    public data(Long id, String time , String org_text, String mod_text, String path_text  ) {
        this.id=id;
        this.time = time;
        this.org_text = org_text;
        this.mod_text = mod_text;
        this.path_text = path_text;
        this.selected= false;

    }

    public String getOrg_text() {
        return org_text;
    }

    public void setOrg_text(String org_text) {
        this.org_text = org_text;
    }

    public String getMod_text() {
        return mod_text;
    }

    public void setMod_text(String mod_text) {
        this.mod_text = mod_text;
    }

    public String getPath_text() {
        return path_text;
    }

    public void setPath_text(String path_text) {
        this.path_text = path_text;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
