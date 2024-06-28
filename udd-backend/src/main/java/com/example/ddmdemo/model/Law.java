package com.example.ddmdemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "laws")
public class Law {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "server_filename")
    private String serverFilename;

    public Law(final String mimeType, final String serverFilename) {
        this.mimeType = mimeType;
        this.serverFilename = serverFilename;
    }
}
