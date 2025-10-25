package com.workintech.s19d1.dto;

import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.entity.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActorRequest {

    // Tekil actor bilgisi (muhtemelen update senaryosu için)
    private Actor actor;

    // Bu actor'ün ilişkilendirileceği filmler
    private List<Movie> movies;

    // Basit alanlar (ilk versiyonda koymuştuk)
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthDate;
}
