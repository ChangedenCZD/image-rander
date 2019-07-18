package model;

import java.util.List;

public class PosterConfigModel {
  private String id;
  private PosterSizeModel size;
  private List<PosterElementModel> elements;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public PosterSizeModel getSize() {
    return size;
  }

  public void setSize(PosterSizeModel size) {
    this.size = size;
  }

  public List<PosterElementModel> getElements() {
    return elements;
  }

  public void setElements(List<PosterElementModel> elements) {
    this.elements = elements;
  }
}
