package com.example.ricardopessoa.androidgooglebooks.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Representation of Images of a specific Book
 * */
public class ImageLinks {

  @SerializedName("smallThumbnail")
  @Expose
  private String smallThumbnail;
  @SerializedName("thumbnail")
  @Expose
  private String thumbnail;

  public String getSmallThumbnail() {
    return smallThumbnail;
  }

  public void setSmallThumbnail(String smallThumbnail) {
    this.smallThumbnail = smallThumbnail;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

}