package tech.boumahdi.quickdirtyblog.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @Column(columnDefinition = "text")
    private String content;

    private String tags;

    private String date;

    private boolean draft;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Author author;

    public void setDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.date = dateFormat.format(date);
    }
}
