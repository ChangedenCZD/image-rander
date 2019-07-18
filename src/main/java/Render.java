import com.alibaba.fastjson.JSON;
import model.PosterConfigModel;
import utils.ImageRender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Render {
  public static void main(String[] args) {
    String resources = new File("").getAbsolutePath()
        + "/src/main/resources/";
    String imagePath = resources + "image.jpg";
    try {
      BufferedReader reader = new BufferedReader(new FileReader(resources + "template_0.json"));
      StringBuilder configContent = new StringBuilder();
      String s;
      while ((s = reader.readLine()) != null) {
        configContent.append(System.lineSeparator()).append(s);
      }
      reader.close();
      PosterConfigModel config = JSON.parseObject(configContent.toString(), PosterConfigModel.class);

      ImageRender imageRender = ImageRender.create(config.getSize());
      config.getElements()
          .forEach(element -> {
            if (element.isShow()) {
              if (element.isRefContent()) { // 为引用内容，则从对应表中查询结果
                // TODO 测试用的数据
                switch (element.getRefTarget()) {
                  case ProductImage:
                    element.setContent("http://static1.shuangkuai.co/7747b14cdb9d45f889cdf87cf7ee076c");
                    break;
                  case ProductName:
                    element.setContent("一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二");
                    break;
                  case ProductPrice:
                    element.setContent("￥" + 8888.88);
                    break;
//                  case ProductMPCode:
//                    element.setContent("https://fastdfs.shuangkuai.co/group1/M00/00/3C/rB4AT10v226APypoAAD6XXx6ylg393.png");
//                    break;
                }
              }
              imageRender.drawElement(element);
            }
          });
      if (imageRender.output(new File(imagePath), "JPG")) {
        System.out.println("生成成功");
      } else {
        System.out.println("生成失败");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println();
  }
}
