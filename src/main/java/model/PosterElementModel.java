package model;

import utils.U;

public class PosterElementModel {
  private int order;
  private String type;
  private String content;
  private String color;
  private PosterSizeModel size;
  private PosterPositionModel position;
  private TextOptions textOptions;
  private boolean show;

  public PosterSizeModel getSize() {
    return size;
  }

  public void setSize(PosterSizeModel size) {
    this.size = size;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public PosterElementType getType() {
    return PosterElementType.match(type);
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public PosterPositionModel getPosition() {
    return position;
  }

  public void setPosition(PosterPositionModel position) {
    this.position = position;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public boolean isRefContent() {
    return !U.isEmpty(content) && content.startsWith("ref://");
  }

  public boolean isUrlContent() {
    return !U.isEmpty(content) && (content.startsWith("http://") || content.startsWith("https://"));
  }

  public PosterElementRef getRefTarget() {
    if (isRefContent()) {
      return PosterElementRef.match(content.replace("ref://", ""));
    }
    return null;
  }

  public boolean isShow() {
    return show;
  }

  public void setShow(boolean show) {
    this.show = show;
  }

  public TextOptions getTextOptions() {
    return textOptions;
  }

  public void setTextOptions(TextOptions textOptions) {
    this.textOptions = textOptions;
  }

  public static class TextOptions {
    private int fontSize;
    private int lineHeight;
    private int maxLines;

    public int getFontSize() {
      return fontSize;
    }

    public void setFontSize(int fontSize) {
      this.fontSize = fontSize;
    }

    public int getLineHeight() {
      return lineHeight;
    }

    public void setLineHeight(int lineHeight) {
      this.lineHeight = lineHeight;
    }

    public int getMaxLines() {
      return maxLines;
    }

    public void setMaxLines(int maxLines) {
      this.maxLines = maxLines;
    }
  }
}
