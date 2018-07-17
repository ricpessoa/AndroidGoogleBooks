package com.example.ricardopessoa.androidgooglebooks.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/***
 *
 * VolumeInfo class represent a book details
 *
 */

public class VolumeInfo {

  @SerializedName("title")
  @Expose
  private String title;

  @SerializedName("subtitle")
  @Expose
  private String subtitle;

  @SerializedName("authors")
  @Expose
  private List<String> authors = null;

  @SerializedName("publishedDate")
  @Expose
  private String publishedDate;

  @SerializedName("averageRating")
  @Expose
  private double averageRating;

  @SerializedName("ratingsCount")
  @Expose
  private int ratingsCount;

  @SerializedName("printType")
  @Expose
  private String printType;

  @SerializedName("imageLinks")
  @Expose
  private ImageLinks imageLinks;

  @SerializedName("description")
  @Expose
  private String description;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public List<String> getAuthors() {
    return authors;
  }

  public void setAuthors(List<String> authors) {
    this.authors = authors;
  }

  public String getPublishedDate() {
    return publishedDate;
  }

  public void setPublishedDate(String publishedDate) {
    this.publishedDate = publishedDate;
  }

  public ImageLinks getImageLinks() {
    return imageLinks;
  }

  public void setImageLinks(ImageLinks imageLinks) {
    this.imageLinks = imageLinks;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getAverageRating() {
    return averageRating;
  }

  public void setAverageRating(double averageRating) {
    this.averageRating = averageRating;
  }

  public String getPrintType() {
    return printType;
  }

  public void setPrintType(String printType) {
    this.printType = printType;
  }

  public int getRatingsCount() {
    return ratingsCount;
  }

  public void setRatingsCount(int ratingsCount) {
    this.ratingsCount = ratingsCount;
  }
}

