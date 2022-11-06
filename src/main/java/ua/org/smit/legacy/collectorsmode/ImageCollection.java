package ua.org.smit.legacy.collectorsmode;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import ua.org.smit.common.model.filed.id.image.ImageId;
import ua.org.smit.common.filesystem.FileCms;
import ua.org.smit.common.filesystem.FolderCms;
import ua.org.smit.common.filesystem.TxtFile;
import ua.org.smit.common.model.time.CreatedTime;

public class ImageCollection extends FolderCms {

    private static final Logger log = Logger.getLogger(ImageCollection.class);

    private final List<ImageCollect> imagesCollect = new ArrayList<>();
    private final Sorting sorting = new Sorting();

    public ImageCollection(String pathname) {
        super(pathname);
        this.mkdir();
        initList();
    }

    public List<ImageId> getImagesIds() {
        List<ImageId> ids = new ArrayList<>();
        for (ImageCollect imageCollect : imagesCollect) {
            ids.add(imageCollect.getImageId());
        }
        return ids;
    }
    
    public List<Integer> getImagesIds2() {
        List<Integer> ids = new ArrayList<>();
        for (ImageCollect imageCollect : imagesCollect) {
            ids.add(imageCollect.getImageId().getValue());
        }
        return ids;
    }

    public void addImageCollect(ImageId imageId) {

        ImageCollect imageCollect = new ImageCollect(imageId, new CreatedTime());

        TxtFile txtFile = new TxtFile(this + File.separator + imageId.getValue() + ".txt");
        txtFile.addToFile(imageCollect.getCreatedTime().toString());
        imagesCollect.add(imageCollect);

        log.info("Added image collect: '" + imageCollect + "'");
    }

    private void initList() {
        List<ImageCollect> items = new ArrayList<>();
        for (FileCms imgFile : this.getFilesTlx()) {
            TxtFile txtFile = new TxtFile(imgFile);
            String fileName = txtFile.getNameWithoutExtension();

            int id = Integer.valueOf(fileName);
            ImageId imageId = new ImageId(id);

            CreatedTime createdTime = new CreatedTime(txtFile.readFirstValue());
            items.add(new ImageCollect(imageId, createdTime));
        }

        imagesCollect.addAll(sorting.sortImgCollect(items));
    }

    public List<ImageCollect> getImagesCollect() {
        return imagesCollect;
    }

    boolean isCollected(ImageId imageId) {
        for (ImageCollect imageCollect : imagesCollect) {
            if (imageCollect.getImageId().equals(imageId)) {
                return true;
            }
        }
        return false;
    }

    void remove(ImageId imageId) {
        FileCms file = new FileCms(this + File.separator + imageId.getValue() + ".txt");
        file.delete();

        Iterator<ImageCollect> iterator = imagesCollect.iterator();
        while (iterator.hasNext()) {
            ImageCollect imageCollect = iterator.next();
            if (imageCollect.getImageId().equals(imageId)) {
                iterator.remove();
            }

        }
    }

}
