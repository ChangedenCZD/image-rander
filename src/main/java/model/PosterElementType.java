package model;

public enum PosterElementType {
  FillRectangle("fillRectangle"), Image("image"), Text("text");

  private String type;

  PosterElementType(String type) {
    this.type = type;
  }

  public static PosterElementType match(String type) {
    PosterElementType[] valueArray = values();
    int length = valueArray.length;
    for (int i = 0; i < length; i++) {
      PosterElementType item = valueArray[i];
      if (item.type.equals(type)) {
        return item;
      }
    }
    return null;
  }
}
