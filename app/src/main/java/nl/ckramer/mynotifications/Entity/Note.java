package nl.ckramer.mynotifications.Entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Note {

    @Id(assignable = false)
    private long id;

    private String title;

    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
