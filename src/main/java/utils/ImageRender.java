package utils;

import model.PosterElementModel;
import model.PosterPositionModel;
import model.PosterSizeModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageRender {

  private BufferedImage bufferedImage;
  private Graphics2D graphics2D;

  private ImageRender(PosterSizeModel size) {
    bufferedImage = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_RGB);
    graphics2D = bufferedImage.createGraphics();
  }

  public static ImageRender create(PosterSizeModel size) {
    return new ImageRender(size);
  }

  public boolean output(File file, String ext) {
    try {
      graphics2D.dispose();
      ImageIO.write(bufferedImage, ext, file);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      bufferedImage = null;
      graphics2D = null;
    }
    return true;
  }

  /**
   * @param text       文本
   * @param lineHeight 行高
   * @param maxWidth   行宽
   * @param maxLine    最大行数
   * @param left       左边距
   * @param top        上边距
   * @param trim       是否修剪文本（1、去除首尾空格，2、将多个换行符替换为一个）
   * @param lineIndent 是否首行缩进
   */
  private void drawString(String text, float lineHeight, float maxWidth, int maxLine, float left,
                         float top, boolean trim, boolean lineIndent) {
    if (text == null || text.length() == 0) return;
    if (trim) {
      text = text.replaceAll("\\n+", "\n").trim();
    }
    if (lineIndent) {
      text = "　　" + text.replaceAll("\\n", "\n　　");
    }
    drawString(text, lineHeight, maxWidth, maxLine, left, top);
  }

  /**
   * @param text       文本
   * @param lineHeight 行高
   * @param maxWidth   行宽
   * @param maxLine    最大行数
   * @param left       左边距
   * @param top        上边距
   */
  private void drawString(String text, float lineHeight, float maxWidth, int maxLine, float left,
                          float top) {
    if (text == null || text.length() == 0) return;

    FontMetrics fm = graphics2D.getFontMetrics();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      sb.append(c);
      int stringWidth = fm.stringWidth(sb.toString());
      if (c == '\n' || stringWidth > maxWidth) {
        if (c == '\n') {
          i += 1;
        }
        if (maxLine > 1) {
          graphics2D.drawString(text.substring(0, i), left, top);
          drawString(text.substring(i), lineHeight, maxWidth, maxLine - 1, left, top + lineHeight);
        } else {
          graphics2D.drawString(text.substring(0, i - 1) + "…", left, top);
        }
        return;
      }
    }
    graphics2D.drawString(text, left, top);
  }

  private Color parseColor(String color) {
    return new Color(Integer.parseInt(color.substring(1, 3), 16),
        Integer.parseInt(color.substring(3, 5), 16),
        Integer.parseInt(color.substring(5, 7), 16));
  }

  private void setColor(String color) {
    if (!U.isEmpty(color)) {
      graphics2D.setColor(parseColor(color));
    }
  }

  private void setFontSize(int size) {
    graphics2D.setFont(new Font("微软雅黑", Font.PLAIN, Math.max(12, size)));
  }

  public void drawElement(PosterElementModel element) {
    setColor(element.getColor());
    PosterSizeModel size = element.getSize();
    PosterPositionModel position = element.getPosition();
    switch (element.getType()) {
      case FillRectangle:
        drawBackground(size, position);
        break;
      case Image:
        if (element.isUrlContent()) {
          drawImage(element.getContent(), size, position);
        }
        break;
      case Text:
        PosterElementModel.TextOptions textOptions = element.getTextOptions();
        setFontSize(textOptions.getFontSize());
        drawString(element.getContent(), textOptions.getLineHeight(),
            size.getWidth(), textOptions.getMaxLines(),
            position.getLeft(), position.getTop(),
            true, false);
        break;
      default:
        break;
    }
  }

  private void drawBackground(PosterSizeModel size, PosterPositionModel position) {
    graphics2D.fillRect(position.getLeft(), position.getTop(), size.getWidth(), size.getHeight());
  }


  private void drawImage(String src, PosterSizeModel size, PosterPositionModel position) {
    try {
      URL url = new URL(src);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      DataInputStream in = new DataInputStream(connection.getInputStream());
      graphics2D.drawImage(ImageIO.read(in), position.getLeft(), position.getTop(), size.getWidth(), size.getHeight(), null, null);
      in.close();
      connection.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
