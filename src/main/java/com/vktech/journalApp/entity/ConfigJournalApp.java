package com.vktech.journalApp.entity;



import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "config_journal_app")
@Data
@Getter
@Setter
@NoArgsConstructor

public class ConfigJournalApp {


    private  String key;
    private  String value;



}
