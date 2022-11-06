package ua.org.smit.legacy.collectorsmode;

import ua.org.smit.common.model.filed.id.image.ImageId;
import ua.org.smit.common.model.time.CreatedTime;


public class ImageCollect {

    private final ImageId imageId;
    private final CreatedTime createdTime;

    public ImageCollect(ImageId imageId, CreatedTime createdTime) {
        this.imageId = imageId;
        this.createdTime = createdTime;
    }

    public ImageId getImageId() {
        return imageId;
    }

    public CreatedTime getCreatedTime() {
        return createdTime;
    }

    @Override
    public String toString() {
        return "ImageCollect{" + "imageId=" + imageId + ", createdTime=" + createdTime + '}';
    }

}
