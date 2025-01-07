package dev.mike.core.search;

import java.util.List;

public class ObjectService {
    private String name;
    private String link;
    private String mappedTo;
    private List<String> preprocessors;
    private List<String> postprocessors;

    public void setName(String name) {
        this.name = name;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setMappedTo(String mappedTo) {
        this.mappedTo = mappedTo;
    }

    public void setPreprocessors(List<String> preprocessors) {
        this.preprocessors = preprocessors;
    }

    public void setPostprocessors(List<String> postprocessors) {
        this.postprocessors = postprocessors;
    }


    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getMappedTo() {
        return mappedTo;
    }

    public List<String> getPreprocessors() {
        return preprocessors;
    }

    public List<String> getPostprocessors() {
        return postprocessors;
    }
}
