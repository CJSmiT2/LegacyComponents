package ua.org.smit.legacy.collectorsmode;

import ua.org.smit.common.model.field.cr.Cr;
import ua.org.smit.common.filesystem.FolderCms;


public class CrFromSale extends FolderCms {

    private final Cr cr = new Cr();

    public CrFromSale(String path) {
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
