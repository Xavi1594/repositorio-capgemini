package com.example.demo_spring_batch.models;

import lombok.Data;

@Data
public class PhotoDTO {
    private String id, author, url, download_url;
    private int width, height;

    public PhotoDTO() {
    }
}
