package model;

public enum PosterElementRef {
  ProductImage("productImage"),
  ProductAdImage("productAdImage"),
  ProductName("productName"),
  ProductPrice("productPrice"),

  StoreName("storeName"),
  StoreLogo("storeLogo"),

  SalesName("salesName"),
  SalesPortrait("salesPortrait"),
  SalesPosition("salesPosition");

  String type;

  PosterElementRef(String type) {
    this.type = type;
  }


  public static PosterElementRef match(String type) {
    PosterElementRef[] valueArray = values();
    int length = valueArray.length;
    for (int i = 0; i < length; i++) {
      PosterElementRef item = valueArray[i];
      if (item.type.equals(type)) {
        return item;
      }
    }
    return null;
  }
}
