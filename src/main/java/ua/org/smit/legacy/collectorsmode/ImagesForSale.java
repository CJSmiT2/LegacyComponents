package ua.org.smit.legacy.collectorsmode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ua.org.smit.common.model.filed.id.image.ImageId;
import ua.org.smit.common.filesystem.TxtFile;

class ImagesForSale extends TxtFile {

    public ImagesForSale(String path) {
        super(path);
        super.makeEmptyIfNotExist();
    }

    void add(ImageId imageId) {
        super.add(imageId.getValue());
    }

    void remove(ImageId imageId) {
        List<Integer> ids = super.readIntegers();
        Iterator<Integer> i = ids.iterator();
        while (i.hasNext()) {
            int id = i.next();
            if (id == imageId.getValue()) {
                i.remove();
            }
        }
        super.makeEmpty();
        super.addAll(ids);
    }

    List<ImageId> getAll() {
        List<ImageId> ids = new ArrayList<>();
        for (Integer number : super.readIntegers()) {
            ids.add(new ImageId(number));
        }
        return ids;
    }

}
