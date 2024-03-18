import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Attraction {

    private String location;
    private String name;
    private String description;
    private List<String> tags = new ArrayList<>();
    private int id;



    public Attraction(int id, String name, String description, String location, List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.tags = tags;

    }

    public static Attraction createAttraction(int id, String name, String descr, String loc, List<String> tags) {
        Attraction attraction = new Attraction(id, name, descr, loc, tags);
        return attraction;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "Attraction{" +
                "location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", id=" + id +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}

