package ua.org.smit.legacy.collectorsmode.values;

import ua.org.smit.common.model.field.cr.Cr;
import ua.org.smit.common.filesystem.FolderCms;


public class Spent extends FolderCms {

    private final Cr cr = new Cr();

    public Spent(String pathname) {
        super(pathname);
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

    public void addInToValue(Cr dealPrice) {
        cr.add(dealPrice);
        writeValue();
    }

}
