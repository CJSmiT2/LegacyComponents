package ua.org.smit.legacy.tags;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import ua.org.smit.common.model.field.id.Id;
import ua.org.smit.common.filesystem.FileCms;
import ua.org.smit.common.filesystem.FolderCms;

public class TagsService {

    private final FolderCms serviceFolder;
    private final FolderCms tagsFolder;

    private final List<Tag> tags = new ArrayList<>();

    public TagsService(FolderCms serviceFolder) {
        this.serviceFolder = serviceFolder;
        this.tagsFolder = new FolderCms(serviceFolder + File.separator + "tags");
        init(tagsFolder);
    }

    private void init(FolderCms serviceFolder) {
        for (FileCms file : serviceFolder.getFilesTlx()) {
            tags.add(new Tag(file));
        }
    }

    public void addId(TagName tagName, Id id) {
        for (Tag tag : tags) {
            if (tag.getTagName().equals(tagName)) {
                tag.addId(id);
                return;
            }
        }
        throw new RuntimeException("Can find tagName = " + tagName.getValue());
    }

    public void addIds(TagName tagName, List<Id> ids) {
        for (Tag tag : tags) {
            if (tag.getTagName().equals(tagName)) {
                tag.addIds(ids);
                return;
            }
        }
        throw new RuntimeException("Can find tagName = " + tagName.getValue());
    }

    public List<Tag> getAllTags() {
        return tags;
    }

    public List<Tag> getWithoutThisTags(Id id) {
        List<Tag> alltags = new ArrayList<>();
        alltags.addAll(tags);
        alltags.removeAll(getById(id));
        return alltags;
    }

    public List<Tag> getById(Id id) {
        List<Tag> sorted = new ArrayList<>();
        for (Tag tag : tags) {
            if (tag.hasId(id)) {
                sorted.add(tag);
            }
        }
        return sorted;
    }

    public void removeId(TagName tagName, Id id) {
        for (Tag tag : tags) {
            if (tag.getTagName().equals(tagName)) {
                tag.removeId(id);
                return;
            }
        }
        throw new RuntimeException("Can find tagName = " + tagName.getValue());
    }

    public void removeId(TagName tagName, List<Id> ids) {
        for (Tag tag : tags) {
            if (tag.getTagName().equals(tagName)) {
                tag.removeIds(ids);
                return;
            }
        }
        throw new RuntimeException("Can find tagName = " + tagName.getValue());
    }

    public List<Id> getByTag(TagName tagName) {
        for (Tag tag : tags) {
            if (tag.getTagName().equals(tagName)) {
                return tag.getIds();
            }
        }
        throw new RuntimeException("Can find tagName = " + tagName.getValue());
    }

}
