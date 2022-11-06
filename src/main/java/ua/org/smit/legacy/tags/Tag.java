package ua.org.smit.legacy.tags;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import ua.org.smit.common.model.field.id.Id;
import ua.org.smit.common.model.field.id.IdsFile;
import ua.org.smit.common.filesystem.FileCms;
import ua.org.smit.common.filesystem.TxtFile;


public class Tag extends TxtFile {
    
    private final TagName tagName;
    private final List<Id> ids = new ArrayList<>();
    
    public Tag(FileCms file) {
        super(file);
        tagName = new TagName(file.getNameWithoutExtension());
        ids.addAll(new IdsFile(file).read());
    }
    
    public List<Id> getIds() {
        return ids;
    }
    
    public TagName getTagName() {
        return tagName;
    }
    
    public Id getRandomId() {
        Random random = new Random();
        int min = 0;
        int max = ids.size() - 1;
        int randomId = random.nextInt(max - min) + min;
        return ids.get(randomId);
    }
    
    void addId(Id id) {
        if (!hasId(id)) {
            new IdsFile(this).addId(id);
            ids.add(id);
        }
    }
    
    void addIds(List<Id> ids) {
        for (Id id : ids) {
            addId(id);
        }
    }
    
    boolean hasId(Id idInput) {
        for (Id id : ids) {
            if (id.getValue() == idInput.getValue()) {
                return true;
            }
        }
        return false;
    }
    
    void removeIds(List<Id> ids) {
        for (Id id : ids) {
            removeId(id);
        }
    }
    
    void removeId(Id idRemove) {
        Iterator<Id> i = ids.iterator();
        while (i.hasNext()) {
            Id id = i.next();
            if (id.getValue() == idRemove.getValue()) {
                i.remove();
                break;
            }
        }
        
        new IdsFile(this).reWrite(ids);
    }
    
}
