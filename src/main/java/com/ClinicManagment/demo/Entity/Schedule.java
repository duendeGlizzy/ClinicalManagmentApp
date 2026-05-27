package com.ClinicManagment.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.query.Procedure;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int scheduleId;

    private DayOfWeek dayOfWeek;

    private Time startTime;

    private Time endTime;

    private Boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

}
