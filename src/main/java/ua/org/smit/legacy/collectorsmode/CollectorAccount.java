package ua.org.smit.legacy.collectorsmode;

import java.io.File;
import ua.org.smit.common.filesystem.FolderCms;
import org.apache.log4j.Logger;
import ua.org.smit.common.model.field.cr.Cr;
import ua.org.smit.common.model.filed.id.album.AlbumId;
import ua.org.smit.common.model.filed.id.image.ImageId;
import ua.org.smit.common.model.filed.id.user.UserAuthId;
import ua.org.smit.legacy.collectorsmode.values.Credit;
import ua.org.smit.legacy.collectorsmode.values.Debit;
import ua.org.smit.legacy.collectorsmode.values.Spent;
import ua.org.smit.legacy.collectorsmode.values.SwingCr;

public class CollectorAccount extends FolderCms {

    private static final Logger log = Logger.getLogger(CollectorAccount.class);

    public static final String COLLECTOR_ACCOUNT = "collector_account";

    private final UserAuthId owner = new UserAuthId();

    private final Debit debit;
    private final Spent spent;
    private final Credit credit;
    private final CrFromSale crFromSale;

    private final ImageCollection imageCollection;

    public CollectorAccount(File file) {
        super(file);
        owner.initFromDisc(this);

        debit = new Debit(super.getAbsoluteFile() + File.separator + "debit");
        debit.readValue();

        spent = new Spent(super.getAbsoluteFile() + File.separator + "spent");
        spent.readValue();

        credit = new Credit(super.getAbsoluteFile() + File.separator + "credit");
        credit.readValue();

        crFromSale = new CrFromSale(super.getAbsoluteFile() + File.separator + "cr_from_sale");
        crFromSale.readValue();

        imageCollection = new ImageCollection(super.getAbsoluteFile() + File.separator + "image_collection");
    }

    public void writeFieldsOnDisk() {
        debit.writeValue();
        spent.writeValue();
        credit.writeValue();
        crFromSale.writeValue();
    }

    boolean isEnoughCr(Cr priceForSale) {
        return getCurrent() >= priceForSale.getValue();
    }

    public long getCurrent() {
        long debit = this.debit.getCr().getValue();
        long credit = this.credit.getCr().getValue();
        long crFromSale = this.crFromSale.getCr().getValue();

        long spend = this.spent.getCr().getValue();

        return (debit + credit + crFromSale) - spend;
    }

    public void setDebit(long value) {
        this.debit.getCr().setValue(value);
    }

    boolean isCollectedThisImage(ImageId imageId) {
        return imageCollection.isCollected(imageId);
    }

    public Debit getDebit() {
        return debit;
    }

    public Spent getSpent() {
        return spent;
    }

    public Credit getCredit() {
        return credit;
    }

    public UserAuthId getOwner() {
        return owner;
    }

    public ImageCollection getImageCollection() {
        return imageCollection;
    }

    public CrFromSale getCrFromSale() {
        return crFromSale;
    }

    void swingCr(ImageId imageId, Cr debit) {
        FolderCms folder = new FolderCms(this.getAbsolutePath() + File.separator + "swing_cr");
        folder.mkdir();
        SwingCr swingCr = new SwingCr(folder.getAbsolutePath());
        swingCr.write(imageId, debit);
    }

    void swingCrAlbum(AlbumId albumId, Cr debit) {
        FolderCms folder = new FolderCms(this.getAbsolutePath() + File.separator + "swing_cr_album");
        folder.mkdir();
        SwingCr swingCr = new SwingCr(folder.getAbsolutePath());
        swingCr.write(albumId, debit);
    }

    boolean isHasImages() {
        return !imageCollection.getImagesCollect().isEmpty();
    }

    public void spend(Cr cr) {
        this.debit.getCr().remove(cr);
        this.spent.addInToValue(cr);
    }
    
    public String getNickName(){
        String[] name = this.getName().split("_");
        return name[1];
    }
    


}
