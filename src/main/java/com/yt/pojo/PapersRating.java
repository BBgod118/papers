package com.yt.pojo;

/**
 * 论文成绩实体类
 *
 * @author yt
 * @date 2019/10/3 - 8:09
 */
public class PapersRating {
  /** 论文名称 */
  private String thesis_title;

  /** 论文上传日期 */
  private String upload_date;

  /** 论文得分 */
  private Integer fraction;

  public PapersRating() {}

  public PapersRating(String thesis_title, String upload_date, Integer fraction) {
    this.thesis_title = thesis_title;
    this.upload_date = upload_date;
    this.fraction = fraction;
  }

  public String getThesis_title() {
    return thesis_title;
  }

  public void setThesis_title(String thesis_title) {
    this.thesis_title = thesis_title;
  }

  public String getUpload_date() {
    return upload_date;
  }

  public void setUpload_date(String upload_date) {
    this.upload_date = upload_date;
  }

  public Integer getFraction() {
    return fraction;
  }

  public void setFraction(Integer fraction) {
    this.fraction = fraction;
  }

  @Override
  public String toString() {
    return "PapersRating{"
        + "thesis_title='"
        + thesis_title
        + '\''
        + ", upload_date='"
        + upload_date
        + '\''
        + ", fraction="
        + fraction
        + '}';
  }
}
