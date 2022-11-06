package ua.org.smit.legacy.collectorsmode.values;

import ua.org.smit.common.model.field.cr.Cr;
import ua.org.smit.common.filesystem.FolderCms;


public class Credit extends FolderCms {

    private final Cr cr = new Cr();

    public Credit(String path) {
        super(path);
    }

    public Cr getCr() {
        return cr;
    }

    public void readValue() {
        cr.initFromDisc(this);
    }

    public void writeValue() {
        cr.writeOnDisc(this);
    }

}
