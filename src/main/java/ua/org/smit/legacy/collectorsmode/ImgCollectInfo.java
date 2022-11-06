package ua.org.smit.legacy.collectorsmode;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import ua.org.smit.common.model.field.cr.Cr;
import ua.org.smit.common.model.filed.id.image.ImageId;
import ua.org.smit.common.filesystem.FolderCms;
import ua.org.smit.common.model.filed.id.user.UserAuthId;
import ua.org.smit.common.model.time.CreatedTime;

public class ImgCollectInfo extends FolderCms {

    public static final String IMG_COLLECT_INFO = "img_collect_info";

    private static final Logger log = Logger.getLogger(ImgCollectInfo.class);

    private final Cr priceForSale;
    private final List<Deal> deals = new ArrayList<>();
    private final Sorting sorting = new Sorting();

    // read
    public ImgCollectInfo(File imageFolder) {
        super(imageFolder);
        readDeals(this);

        priceForSale = new Cr();
        priceForSale.initFromDisc(this);
    }

    // create
    public ImgCollectInfo(File imageFolder, long DEFAULT_PRICE) {
        super(imageFolder);

        priceForSale = new Cr(DEFAULT_PRICE);
        priceForSale.writeOnDisc(this);
    }

    public ImageId getImageId() {
        return new ImageId(this.getName());
    }

    private void readDeals(FolderCms thisFolder) {
        List<Deal> items = new ArrayList<>();
        for (FolderCms dealFolder : thisFolder.getFoldersCms()) {
            items.add(new Deal(dealFolder));
        }

        deals.addAll(sorting.sortDeals(items));
    }

    public boolean isCanBuy(CollectorAccount collectorAccaunt) {
        if (priceForSale.getValue() == 0) {
            return false;
        } else {
            return collectorAccaunt.isEnoughCr(priceForSale);
        }
    }

    public boolean isForSale() {
        return this.getPriceForSale().getValue() > 0;
    }

    public Cr getPriceForSale() {
        return priceForSale;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public List<Deal> getDealsReverse() {
        List<Deal> dealsReversed = new ArrayList(deals);
        Collections.reverse(dealsReversed);
        return dealsReversed;
    }

    public Deal getLastDeal() {
        return deals.get(deals.size() - 1);
    }

    Optional<UserAuthId> getLatestCollector() {
        if (isHasCollector()) {
            return Optional.of(getLastDeal().getOwner());
        }
        return Optional.empty();
    }

    void makeDeal(CollectorAccount buyer) {
        FolderCms dealFolder = new FolderCms(this + File.separator + new CreatedTime().toString());

        Cr dealPrice = new Cr(priceForSale.getValue());
        Deal deal = new Deal(dealFolder, buyer.getOwner(), dealPrice);
        priceForSale.setValue(0);
        priceForSale.writeOnDisc(this);

        deals.add(deal);

        buyer.getImageCollection().addImageCollect(this.getImageId());
        buyer.getSpent().addInToValue(dealPrice);
    }

    void setPriceForSale(Cr price) {
        priceForSale.setValue(price.getValue());
        priceForSale.writeOnDisc(this);
    }

    public boolean isHasCollector() {
        return this.deals.size() > 0;
    }

}
