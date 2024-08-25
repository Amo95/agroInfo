package com.android.agroinfo.model;

import androidx.annotation.NonNull;

public class Images {

    private String imageUrl;
    private String name;
    private String description;

    public Images(String name, String imageUrl, String description) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Images(ImagesBuilder builder) {
        this.imageUrl = builder.imageUrl;
        this.name = builder.name;
        this.description = builder.description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return "Contact{" +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public static ImagesBuilder builder() {
        return new ImagesBuilder();
    }

    public static class ImagesBuilder {
        private String description;
        private String imageUrl;
        private String name;

        public ImagesBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ImagesBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ImagesBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Images build() {
            return new Images(name, imageUrl, description);
        }
    }
}
