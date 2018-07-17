package com.example.ricardopessoa.androidgooglebooks.model;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Book {

  @SerializedName("id")
  @Expose
  private String id;

  //volumeInfo is basically the information of book
  @SerializedName("volumeInfo")
  @Expose
  private VolumeInfo volumeInfo;

  public VolumeInfo getVolumeInfo() {
    return volumeInfo;
  }

  public void setVolumeInfo(VolumeInfo volumeInfo) {
    this.volumeInfo = volumeInfo;
  }

  public String getAuthorsAndYear() {
    String authorsNameYear = "";
    if (volumeInfo.getAuthors() != null) {
      for (String author : volumeInfo.getAuthors()) {
        if (!author.isEmpty()) {
          authorsNameYear = authorsNameYear.concat(author + ", ");
        }
      }
    }

    if (volumeInfo.getPublishedDate() != null && !volumeInfo.getPublishedDate().isEmpty()) {
      authorsNameYear = authorsNameYear.concat(volumeInfo.getPublishedDate());
    }

    return authorsNameYear;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}