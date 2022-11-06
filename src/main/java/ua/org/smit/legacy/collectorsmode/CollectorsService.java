package ua.org.smit.legacy.collectorsmode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import ua.org.smit.common.model.filed.id.image.ImageId;
import ua.org.smit.common.filesystem.FolderCms;

public class CollectorsService {

    private static final Logger log = Logger.getLogger(CollectorAccount.class);

    public static final String COLLECTORS_SERVICE_FOLDER_NAME = "collectors_mod";

    private final DefaultPrice defaultPrice;

    private final FolderCms serviceFolder;
    private final FolderCms imagesFolder;
    private final FolderCms usersFolder;

    private final ImagesForSale imagesForSale;

    public CollectorsService(FolderCms folder) {

        this.defaultPrice = new DefaultPrice(folder.getAbsolutePath());
        this.imagesForSale = new ImagesForSale(folder + File.separator + "images_for_sale.txt");

        this.serviceFolder = folder;

        imagesFolder = new FolderCms(folder + File.separator + "images");
        usersFolder = new FolderCms(folder + File.separator + "users");

    }

    public ImgCollectInfo readInfo(ImageId imageId) {
        ImgCollectInfo imgCollectInfo;

        File imageFolder = new File(imagesFolder + File.separator + imageId.getValue());
        if (!imageFolder.exists()) {
            imageFolder.mkdir();
            imgCollectInfo = new ImgCollectInfo(imageFolder, defaultPrice.getValue());
        } else {
            imgCollectInfo = new ImgCollectInfo(imageFolder);
        }

        return imgCollectInfo;
    }

    private CollectorAccount readAccount(FolderCms accountFolder) {
        return new CollectorAccount(accountFolder);
    }

    public List<CollectorAccount> getCollectorsWithImages() {
        List<CollectorAccount> collectors = new ArrayList<>();
        List<FolderCms> folders = usersFolder.getFoldersCms();

        for (FolderCms folder : folders) {
            try {
                CollectorAccount collectorAccount = readAccount(folder);
                if (collectorAccount.isHasImages()) {
                    collectors.add(collectorAccount);
                }
            } catch (RuntimeException e) {
                log.debug("Cant init folder: " + folder);
            }
        }

        return collectors;
    }

    public List<ImgCollectInfo> getAllCollectedImages() {
        log.info("Start init images info from disk...");
        List<ImgCollectInfo> images = new ArrayList<>();

        for (FolderCms imageFolder : imagesFolder.getFoldersCms()) {
            ImageId imageId = new ImageId(imageFolder.getName());
            ImgCollectInfo imageInfo = this.readInfo(imageId);
            images.add(imageInfo);
        }

        log.info("Complete init images from disk!");
        return images;
    }

    public List<ImgCollectInfo> getImagesForSale() {
        List<ImgCollectInfo> images = new ArrayList<>();
        for (ImgCollectInfo image : readByIds(imagesForSale.getAll())) {
            if (image.isForSale() && image.isHasCollector()) {
                images.add(image);
            }
        }

        return images;
    }

    private List<ImgCollectInfo> readByIds(List<ImageId> ids) {
        List<ImgCollectInfo> images = new ArrayList<>();
        for (ImageId imageId : ids) {
            images.add(readInfo(imageId));
        }
        return images;
    }

    public CollectorAccount getCollector(String nickName) {
        for (CollectorAccount collector : this.getCollectorsWithImages()) {
            if (collector.getNickName().equals(nickName)) {
                return collector;
            }
        }
        throw new RuntimeException("Cant found collector by nickName = '" + nickName + "'");
    }

}
