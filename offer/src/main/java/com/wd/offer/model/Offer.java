package com.wd.offer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("offers")
public class Offer {

    @Id
    private String id;
    private String title;
    private String company;
    private String salary;
    @Indexed(unique = true, name = "meta_url_index_unique")
    private String offerUrl;

}

