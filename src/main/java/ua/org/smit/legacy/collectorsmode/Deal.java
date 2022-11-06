package ua.org.smit.legacy.collectorsmode;

import ua.org.smit.common.model.field.cr.Cr;
import ua.org.smit.common.filesystem.FolderCms;
import ua.org.smit.common.model.filed.id.user.UserAuthId;
import ua.org.smit.common.model.time.CreatedTime;


public class Deal extends FolderCms {

    private final UserAuthId owner;
    private final Cr price;


    //create
    public Deal(FolderCms folder, UserAuthId owner, Cr price) {
        super(folder);
        folder.mkdir();
        this.owner = owner;
        this.price = price;

        this.owner.writeOnDisc(folder);
        this.price.writeOnDisc(folder);
    }

    // read
    public Deal(FolderCms folder) {
        super(folder);

        owner = new UserAuthId();
        owner.initFromDisc(folder);

        price = new Cr();
        price.initFromDisc(folder);
    }

    public Cr getPrice() {
        return price;
    }

    public CreatedTime getCreatedTime() {
        return new CreatedTime(this.getName());
    }

    public UserAuthId getOwner() {
        return owner;
    }

}
