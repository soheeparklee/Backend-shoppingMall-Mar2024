package org.shoppingMall.web.DTO.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoRequestList {
    private Integer photoId;
    private String photoUrl;
    private Boolean photoType; //true: main pic, false: product pic

    public PhotoRequestList(String photoUrl, Boolean photoType) {
        this.photoUrl = photoUrl;
        this.photoType = photoType;
    }
}
