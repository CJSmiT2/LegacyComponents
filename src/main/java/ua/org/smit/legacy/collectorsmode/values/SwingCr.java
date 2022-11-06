package ua.org.smit.legacy.collectorsmode.values;

import java.io.File;
import ua.org.smit.common.model.field.cr.Cr;
import ua.org.smit.common.model.field.id.Id;
import ua.org.smit.common.filesystem.FolderCms;
import ua.org.smit.common.filesystem.TxtFile;

public class SwingCr extends FolderCms {

    public SwingCr(String absolutePath) {
        super(absolutePath);
    }

    public void write(Id id, Cr debit) {
        long value = 0;
        TxtFile txt = new TxtFile(super.getAbsolutePath() + File.separator + id.getValue() + ".txt");
        if (txt.exists()) {
            value = txt.readFirstLong();
            txt.delete();
        }

        value = value + debit.getValue();
        txt.add(value);
    }

}
